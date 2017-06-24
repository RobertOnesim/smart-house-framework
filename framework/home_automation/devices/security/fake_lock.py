from home_automation.devices.security.lock import Lock


class FakeLock(Lock):
    def __init__(self, mac_address, device_user_name, is_on, ip_address, pin_code):
        self._connection = None
        self._mac_address = mac_address
        self._name = device_user_name
        self._state = is_on
        self._pin_code = pin_code
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

    def change_pin_code(self, value):
        self._pin_code = value

    def set_timer(self):
        # TODO ronesim simulate closing using tasks
        pass

    def voice_activation(self):
        # TODO ronesim
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
    def ip_address(self):
        return self._ip_address

    @property
    def state(self):
        return self._state

    @property
    def pin_code(self):
        return self._pin_code
