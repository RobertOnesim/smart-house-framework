from django.http import Http404
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView

from home_control.models import Room
from home_control.serializers import RoomSerializer


class RoomList(APIView):
    """
    List all rooms, or create a new room.
    """
    serializer_class = RoomSerializer

    def get(self, request):
        rooms = Room.objects.all()
        serializer = RoomSerializer(rooms, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = RoomSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.error_messages, status=status.HTTP_400_BAD_REQUEST)


class RoomDetail(APIView):
    """
    Retrieve, update or delete a room instance.
    """

    def get_object(self, room_id):
        try:
            return Room.objects.get(pk=room_id)
        except Room.DoesNotExist:
            raise Http404

    def get(self, request, room_id):
        room = self.get_object(room_id)
        serializer = RoomSerializer(room)
        return Response(serializer.data)

    def put(self, request, room_id):
        room = self.get_object(room_id)
        serializer = RoomSerializer(room, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    def delete(self, request, room_id):
        room = self.get_object(room_id)
        room.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
