from abc import abstractmethod

from home_automation.devices.device import Device


class Plug(Device):
    @property
    @abstractmethod
    def away_mode(self):
        pass

    @property
    @abstractmethod
    def schedule(self):
        pass

    @abstractmethod
    def set_timer(self):
        pass
