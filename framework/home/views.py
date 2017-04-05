from django.shortcuts import get_list_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Room
from .serializers import RoomSerializer

# Create your views here.

class RoomList(APIView):

    def get(self, request):
        rooms = Room.objects.all()
        serializer = RoomSerializer(rooms, many=True)
        return Response(serializer.data)

    def post(self):
        pass