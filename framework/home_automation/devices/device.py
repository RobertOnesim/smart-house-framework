from abc import abstractmethod

from .singleton import Singleton


class Device(metaclass=Singleton):
    @property
    @abstractmethod
    def connection(self):
        pass

    @property
    @abstractmethod
    def name(self):
        pass

    @property
    @abstractmethod
    def mac_address(self):
        pass

    @property
    @abstractmethod
    def ip_address(self):
        pass

    @property
    @abstractmethod
    def state(self):
        pass

    @abstractmethod
    def connect(self):
        pass

    @abstractmethod
    def disconnect(self):
        pass

    @abstractmethod
    def turn_on(self):
        pass

    @abstractmethod
    def turn_off(self):
        pass

    def is_connected(self):
        return self.connection is not None
