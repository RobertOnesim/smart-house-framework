from django.http import Http404
from django.utils import timezone
from rest_framework import status
from rest_framework.response import Response

from home_automation import TPLink
from home_control.home.devices.serializers import PlugSerializer
from home_control.models import Plug
from .device_base import DeviceBaseManager


class PlugManager(DeviceBaseManager):
    def get_object(self, device_id):
        try:
            return Plug.objects.get(pk=device_id)
        except Plug.DoesNotExist:
            raise Http404

    def initialize(self, device_id):
        db_plug = self.get_object(device_id)
        return db_plug, TPLink(db_plug.mac_address, db_plug.name, db_plug.is_on, db_plug.ip_address, db_plug.away_mode,
                               db_plug.schedule)

    def get(self, request, device_id):
        db_plug = self.get_object(device_id)
        serializer = PlugSerializer(db_plug)
        return Response(serializer.data)

    def post(self, request, device_id):
        db_plug, plug = self.initialize(device_id)
        action_type = request.data['action']
        # check action type
        changed = False
        if plug.connect():
            if action_type == 'state':
                # change state (on/off)
                db_plug.room.info = "Plug (" + db_plug.name + ") state was changed."
                self.change_state(db_plug, plug, request.data['state'])
                changed = True
            if changed:
                db_plug.room.date_update = timezone.now()
                db_plug.room.save()
                return Response(status=status.HTTP_200_OK)
        return Response(status=status.HTTP_406_NOT_ACCEPTABLE)
