from rest_framework import serializers

from .devices import LightSerializer
from .models import Room


class RoomSerializer(serializers.ModelSerializer):
    lights = LightSerializer(many=True, read_only=True)

    class Meta:
        model = Room
        fields = ('id', 'name', 'number_of_devices', 'info', 'date_update', 'lights')
