from rest_framework import serializers

from home_control.models import Device, Light


class DeviceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Device
        fields = ('id', 'room', 'name', 'mac_address', 'is_on')


class LightSerializer(DeviceSerializer):
    class Meta(DeviceSerializer.Meta):
        model = Light
        fields = DeviceSerializer.Meta.fields + ('color', 'intensity')
