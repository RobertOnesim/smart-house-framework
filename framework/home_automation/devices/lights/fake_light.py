from home_automation.devices.lights.light import Light


class FakeLight(Light):
    def __init__(self, mac_address, device_user_name, is_on, ip_address, color, intensity):
        self._connection = None
        self._mac_address = mac_address
        self._name = device_user_name
        self._state = is_on
        self._color = color
        self._intensity = intensity
        self._ip_address = ip_address

    def connect(self):
        self._connection = True
        return True

    def disconnect(self):
        self._connection = None

    def turn_on(self):
        self._state = True

    def turn_off(self):
        self._state = False

    def set_color(self, color_list):
        self._color = color_list

    def set_warm_light(self, intensity=1.0):
        self._intensity = intensity

    @property
    def connection(self):
        return self._connection

    @property
    def name(self):
        return self._name

    @property
    def ip_address(self):
        return self._ip_address

    @property
    def mac_address(self):
        return self._mac_address

    @property
    def state(self):
        return self._state

    @property
    def color(self):
        return self._color

    @property
    def intensity(self):
        return self._intensity
