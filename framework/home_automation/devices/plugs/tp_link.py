from pyHS100 import SmartPlug

from .plug import Plug


class TPLink(Plug):
    def __init__(self, mac_address, device_user_name, is_on, ip_address, away_mode, schedule):
        self._connection = None
        self._mac_address = mac_address
        self._name = device_user_name
        self._state = is_on
        self._ip_address = ip_address
        self._away_mode = away_mode
        self._schedule = schedule

    def connect(self):
        self._connection = SmartPlug(self._ip_address)
        return True

    def disconnect(self):
        self._connection = None

    def turn_on(self):
        self._connection.turn_on()
        self._state = True

    def turn_off(self):
        self._connection.turn_off()
        self._state = False

    def set_timer(self):
        pass

    @property
    def ip_address(self):
        return self._ip_address

    @property
    def away_mode(self):
        return self._away_mode

    @property
    def schedule(self):
        return self._schedule

    @property
    def connection(self):
        return self._connection

    @property
    def name(self):
        return self._name

    @property
    def mac_address(self):
        return self._mac_address

    @property
    def state(self):
        return self._state
