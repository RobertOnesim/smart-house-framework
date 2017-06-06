from .webcam import Webcam


class FakeWebcam(Webcam):
    def __init__(self, mac_address, device_user_name, is_on, night_vision):
        self._connection = None
        self._mac_address = mac_address
        self._name = device_user_name
        self._state = is_on
        self._night_vision = night_vision

    def connect(self):
        self._connection = True
        return True

    def disconnect(self):
        self._connection = None

    def turn_on(self):
        self._state = True

    def turn_off(self):
        self._state = False

    def get_image(self):
        pass

    def motion_detection(self):
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
    def night_vision(self):
        return self._night_vision
