from django.http import Http404
from django.utils import timezone
from rest_framework import status
from rest_framework.response import Response

from home_automation import MagicBlue, FakeLight
from home_control.home.devices.serializers import LightSerializer
from home_control.models import Light
from .device_base import DeviceBaseManager


class LightManager(DeviceBaseManager):
    def get_object(self, device_id):
        try:
            return Light.objects.get(pk=device_id)
        except Light.DoesNotExist:
            raise Http404

    def initialize(self, device_id):
        db_light = self.get_object(device_id)
        if db_light.brand == "MagicBlue":
            return db_light, MagicBlue(db_light.mac_address, db_light.name, db_light.is_on, db_light.ip_address,
                                       db_light.color, db_light.intensity)
        if db_light.brand == "FakeLight":
            return db_light, FakeLight(db_light.mac_address, db_light.name, db_light.is_on, db_light.ip_address,
                                       db_light.color, db_light.intensity)

    def get(self, request, device_id):
        db_light = self.get_object(device_id)
        serializer = LightSerializer(db_light)
        return Response(serializer.data)

    def post(self, request, device_id):
        db_light, light = self.initialize(device_id)
        action_type = request.data['action']

        # check action type
        changed = False
        if light.connect():
            if action_type == 'state':
                # change light state (on/off)
                db_light.room.info = "Light (" + db_light.name + ") state was changed."
                self.change_state(db_light, light, request.data['state'])
                changed = True
            elif action_type == 'color':
                # change light color
                db_light.room.info = "Light (" + db_light.name + ") color was changed."
                self.change_color(db_light, light, request.data['color'])
                changed = True
            elif action_type == 'intensity':
                # change light intensity
                db_light.room.info = "Light (" + db_light.name + ") intensity was changed."
                self.change_intensity(db_light, light, request.data['intensity'])
                changed = True
            if changed:
                db_light.room.date_update = timezone.now()
                db_light.room.save()
                return Response(status=status.HTTP_200_OK)
        return Response(status=status.HTTP_406_NOT_ACCEPTABLE)

    def change_color(self, db_light, light, color):
        """ Change light color """
        if db_light.is_on:
            light.set_color([int(x) for x in color.split()])

            # store new info in the database
            db_light.color = color
            db_light.save()
            light.disconnect()

    def change_intensity(self, db_light, light, intensity):
        """ Change light intensity """
        if db_light.is_on:
            light.set_warm_light(float(intensity))

            # store new info in the database
            db_light.intensity = intensity
            db_light.save()
            light.disconnect()
