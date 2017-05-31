from abc import abstractmethod

from home_automation.devices.device import Device


class Lock(Device):
    @property
    @abstractmethod
    def pin_code(self):
        pass

    @abstractmethod
    def set_timer(self):
        pass

    @abstractmethod
    def voice_activation(self):
        pass

    @abstractmethod
    def change_pin_code(self, value):
        pass
