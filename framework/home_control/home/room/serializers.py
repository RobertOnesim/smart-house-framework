from rest_framework import serializers

from home_control.home.devices import (
    LightSerializer,
    PlugSerializer,
    ThermostatSerializer,
    LockSerializer,
    WebcamSerializer)
from home_control.models import Room


class RoomSerializer(serializers.ModelSerializer):
    # add serializer for each type of device
    lights = LightSerializer(many=True, read_only=True)
    plugs = PlugSerializer(many=True, read_only=True)
    thermostats = ThermostatSerializer(many=True, read_only=True)
    locks = LockSerializer(many=True, read_only=True)
    webcams = WebcamSerializer(many=True, read_only=True)

    class Meta:
        model = Room
        fields = '__all__'
