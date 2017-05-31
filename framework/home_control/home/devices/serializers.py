from rest_framework import serializers

from home_control.models import (
    Device,
    Light,
    Plug,
    Thermostat,
    Lock,
    Webcam,
    Product)


class ProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Product
        fields = ('id', 'name', 'type', 'connection_type')


class DeviceSerializer(serializers.ModelSerializer):
    """
        Factory method to create serialized instances for different devices
    """

    @staticmethod
    def get_device_instance(device_type, info):
        if device_type == 'Light':
            return LightSerializer(data=info)
        elif device_type == 'Plug':
            return PlugSerializer(data=info)
        elif device_type == 'Thermostat':
            return ThermostatSerializer(data=info)
        elif device_type == 'Lock':
            return LockSerializer(data=info)
        elif device_type == 'Webcam':
            return WebcamSerializer(data=info)
        raise TypeError('Unknown Factory.')

    class Meta:
        model = Device
        fields = ('id', 'room', 'name', 'mac_address', 'is_on')


class LightSerializer(DeviceSerializer):
    class Meta(DeviceSerializer.Meta):
        model = Light
        fields = DeviceSerializer.Meta.fields + ('color', 'intensity')


class PlugSerializer(DeviceSerializer):
    class Meta(DeviceSerializer.Meta):
        model = Plug
        fields = DeviceSerializer.Meta.fields + ('away_mode', 'schedule')


class ThermostatSerializer(DeviceSerializer):
    class Meta(DeviceSerializer.Meta):
        model = Thermostat
        fields = DeviceSerializer.Meta.fields + ('temperature', 'humidity')


class LockSerializer(DeviceSerializer):
    class Meta(DeviceSerializer.Meta):
        model = Lock
        fields = DeviceSerializer.Meta.fields + ('pin_code', 'voice_activation')


class WebcamSerializer(DeviceSerializer):
    class Meta(DeviceSerializer.Meta):
        model = Webcam
        fields = DeviceSerializer.Meta.fields + ('night_vision', 'motion_detection')
