from .thermostat import Thermostat


class FakeThermostat(Thermostat):
    def __init__(self, mac_address, device_user_name, is_on, temperature, humidity):
        self._connection = None
        self._mac_address = mac_address
        self._name = device_user_name
        self._state = is_on
        self._temperature = temperature
        self._humidity = humidity

    def connect(self):
        self._connection = True
        return True

    def disconnect(self):
        self._connection = None

    def turn_on(self):
        self._state = True

    def turn_off(self):
        self._state = False

    def set_temperature(self, value):
        self._temperature = value

    def set_humidity(self, value):
        self._humidity = value

    def get_motion(self):
        pass

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

    @property
    def temperature(self):
        return self._temperature

    @property
    def humidity(self):
        return self._humidity
