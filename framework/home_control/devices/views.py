from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from .serializers import LightSerializer


class DeviceFactory(object):
    @staticmethod
    def create_device_instance(device_type, data):
        if device_type == 'light':
            return LightSerializer(data=data)


class DeviceManager(APIView):
    """
    Add a new device.
    """

    def post(self, request):
        serializer = DeviceFactory.create_device_instance(request.data['type'], request.data['info'])
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.error_messages, status=status.HTTP_400_BAD_REQUEST)
