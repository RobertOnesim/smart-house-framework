from abc import abstractmethod

from home_automation.devices.device import Device


class Thermostat(Device):
    @property
    @abstractmethod
    def temperature(self):
        pass

    @property
    @abstractmethod
    def humidity(self):
        pass

    @abstractmethod
    def get_motion(self):
        pass

    @abstractmethod
    def set_temperature(self, value):
        pass

    @abstractmethod
    def set_humidity(self, value):
        pass
