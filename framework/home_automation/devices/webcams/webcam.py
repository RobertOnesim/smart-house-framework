from abc import abstractmethod

from home_automation.devices.device import Device


class Webcam(Device):
    @property
    @abstractmethod
    def night_vision(self):
        pass

    @abstractmethod
    def get_image(self):
        pass

    @abstractmethod
    def motion_detection(self):
        pass
