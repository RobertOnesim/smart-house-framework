from django.http import Http404
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from home_automation import MagicBlue
from home_control.home.devices.serializers import LightSerializer
from home_control.models import Light


def get_object(device_id):
    try:
        return Light.objects.get(pk=device_id)
    except Light.DoesNotExist:
        raise Http404


def initialize(device_id):
    db_light = get_object(device_id)
    return db_light, MagicBlue(db_light.mac_address, db_light.name, db_light.is_on,
                               [int(x) for x in db_light.color.split()], db_light.intensity)


class LightManager(APIView):
    def get_object(self, device_id):
        try:
            return Light.objects.get(pk=device_id)
        except Light.DoesNotExist:
            raise Http404

    def initialize(self, device_id):
        db_light = get_object(device_id)
        return db_light, MagicBlue(db_light.mac_address, db_light.name, db_light.is_on, db_light.color,
                                   db_light.intensity)

    def get(self, request, device_id):
        db_light, light = initialize(device_id)
        # connect to the light
        if light.connect():
            if light.state:
                light.turn_on()
            else:
                light.turn_off()
            light.disconnect()
            serializer = LightSerializer(db_light)
            return Response(serializer.data)
        return Response(status=status.HTTP_406_NOT_ACCEPTABLE)

    def post(self, request, device_id):
        db_light, light = initialize(device_id)
        action_type = request.data['action']

        # check action type
        if light.connect():
            if action_type == 'state':
                # change light state (on/off)
                self.change_state(db_light, light, request.data['state'])
                return Response(status=status.HTTP_200_OK)
            if action_type == 'color':
                # change light color
                self.change_color(db_light, light, request.data['color'])
                return Response(status=status.HTTP_200_OK)
            if action_type == 'intensity':
                # change light intensity
                self.change_intensity(db_light, light, request.data['intensity'])
            return Response(status=status.HTTP_200_OK)
        return Response(status=status.HTTP_406_NOT_ACCEPTABLE)

    def change_state(self, db_light, light, state):
        """ Change light state (on/off) """
        if state == "on":
            # turn on
            light.turn_on()
            db_light.is_on = True
        else:
            # turn off
            light.turn_off()
            db_light.is_on = False
        db_light.save()

    def change_color(self, db_light, light, color):
        """ Change light color """
        if db_light.is_on:
            # change color and intensity
            light.set_color([int(x) for x in color.split()])

            # store new info in the database
            db_light.color = color
            db_light.save()
            light.disconnect()

    def change_intensity(self, db_light, light, intensity):
        """ Change light intensity """
        if db_light.is_on:
            # change color and intensity
            light.set_warm_light(float(intensity))

            # store new info in the database
            db_light.intensity = intensity
            db_light.save()
            light.disconnect()
