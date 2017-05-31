import random
from abc import abstractmethod

from home_automation.devices.device import Device


class Light(Device):
    @property
    @abstractmethod
    def color(self):
        pass

    @property
    @abstractmethod
    def intensity(self):
        pass

    @abstractmethod
    def set_color(self, color_list):
        pass

    @abstractmethod
    def set_warm_light(self, intensity):
        pass

    def set_random_color(self):
        self.set_color([random.randint(1, 255) for color in range(3)])
