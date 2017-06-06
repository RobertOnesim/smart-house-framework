from django.http import Http404
from rest_framework import status
from rest_framework.response import Response

from home_automation import FakeWebcam
from home_control.home.devices import WebcamSerializer
from home_control.models import Webcam
from .device_base import DeviceBaseManager


class WebcamManager(DeviceBaseManager):
    def get_object(self, device_id):
        try:
            return Webcam.objects.get(pk=device_id)
        except Webcam.DoesNotExist:
            raise Http404

    def initialize(self, device_id):
        db_webcam = self.get_object(device_id)
        return db_webcam, FakeWebcam(db_webcam.mac_address, db_webcam.name, db_webcam.is_on, db_webcam.night_vision)

    def get(self, request, device_id):
        db_webcam, webcam = self.initialize(device_id)
        # connect to the thermostat
        if webcam.connect():
            if webcam.state:
                webcam.turn_on()
            else:
                webcam.turn_off()
            webcam.disconnect()
            serializer = WebcamSerializer(db_webcam)
            return Response(serializer.data)
        return Response(status=status.HTTP_406_NOT_ACCEPTABLE)

    def post(self, request, device_id):
        db_webcam, webcam = self.initialize(device_id)
        action_type = request.data['action']

        # check action type
        if webcam.connect():
            if action_type == 'state':
                # change state (on/off)
                self.change_state(db_webcam, webcam, request.data['state'])
                return Response(status=status.HTTP_200_OK)
            return Response(status=status.HTTP_200_OK)
        return Response(status=status.HTTP_406_NOT_ACCEPTABLE)
