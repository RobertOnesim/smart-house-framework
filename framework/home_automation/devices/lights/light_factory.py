from .fake_light import FakeLight
from .magic_blue import MagicBlue


class LightFactory():
    @staticmethod
    def create_light_instance(light):
        if light.brand == 'MagicBlue':
            return MagicBlue(light.mac_address, light.name, light.is_on, light.ip_address, light.color,
                             light.intensity)
        if light.brand == 'FakeLight':
            return FakeLight(light.mac_address, light.name, light.is_on, light.ip_address, light.color,
                             light.intensity)
