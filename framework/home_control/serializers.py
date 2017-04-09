from rest_framework import serializers

from .models import Room, Device, Light


class DeviceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Device
        fields = ('id', 'room', 'name', 'mac_address', 'is_on')


class LightSerializer(DeviceSerializer):
    class Meta(DeviceSerializer.Meta):
        model = Light
        fields = DeviceSerializer.Meta.fields + ('color', 'intensity',)


class RoomSerializer(serializers.ModelSerializer):
    lights = LightSerializer(many=True, read_only=True)

    class Meta:
        model = Room
        fields = ('id', 'name', 'number_of_devices', 'lights')
