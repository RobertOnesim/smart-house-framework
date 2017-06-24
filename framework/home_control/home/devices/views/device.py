from django.http import Http404
from django.utils import timezone
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from home_control.home.devices.serializers import ProductSerializer, DeviceSerializer
from home_control.models import (
    Room,
    Product,
    Light,
    Plug,
    Thermostat,
    Lock,
    Webcam)


class DeviceFactory(object):
    @staticmethod
    def create_device_instance(device_type):
        if device_type == 'light':
            return Light
        if device_type == 'plug':
            return Plug
        if device_type == 'thermostat':
            return Thermostat
        if device_type == 'lock':
            return Lock
        if device_type == 'webcam':
            return Webcam


class DeviceManager(APIView):
    """
    Get all products and add/remove a device.
    """

    def get_object(self, device_type, device_id):
        device = DeviceFactory.create_device_instance(device_type)
        try:
            return device.objects.get(pk=device_id)
        except device.DoesNotExist:
            raise Http404

    def get(self, request):
        products = Product.objects.all()
        serializer = ProductSerializer(products, many=True)
        return Response(serializer.data)

    def post(self, request):
        info = dict(room=request.data['roomId'],
                    mac_address=request.data['mac_address'],
                    brand=request.data['brand'],
                    name=request.data['name'],
                    is_on=False,
                    )
        serializer = DeviceSerializer.get_device_instance(request.data['device_type'], info=info)
        if serializer.is_valid():
            # increment number of devices in the room update time and last action
            room = Room.objects.get(pk=info['room'])
            room.number_of_devices += 1
            room.info = "Added a new device"
            room.date_update = timezone.now()
            room.save()
            serializer.save()
            info['id'] = serializer.data['id']
            return Response(info, status=status.HTTP_201_CREATED)
        return Response(serializer.error_messages, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, device_type, device_id):
        device = self.get_object(device_type, device_id)
        # decrement number of devices from that room
        room = device.room
        room.number_of_devices -= 1
        room.info = "Device was removed"
        room.date_update = timezone.now()
        room.save()
        # delete the device
        device.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
