import functools

from bluepy import btle

from home_automation.devices.lights.light import Light

MAGIC_BLUE_SET_COLOR = 0x56
UUID_CHARACTERISTIC_RECEIVED = btle.UUID('ffe4')
UUID_CHARACTERISTIC_WRITE = btle.UUID('ffe9')
UUID_CHARACTERISTIC_NAME = btle.UUID('2a00')


def connection_required(func):
    """Raise an exception before calling the actual function if the device is
    not connection.
    """

    @functools.wraps(func)
    def wrapper(self, *args, **kwargs):
        if self._connection is None:
            raise Exception("Not connected")

        return func(self, *args, **kwargs)

    return wrapper


class MagicBlue(Light):
    def __init__(self, mac_address, device_user_name, is_on, ip_address, color, intensity):
        self._connection = None
        self._addr_type = btle.ADDR_TYPE_PUBLIC
        self._mac_address = mac_address
        self._name = device_user_name
        self._state = is_on
        self._color = color
        self._intensity = intensity
        self._ip_address = ip_address

    def connect(self):
        """
        Connect to device
        :return: True if connection succeed, False otherwise
        """
        try:
            # param: mac_address, addr_type and bluetooth adapter number (default 0)
            connection = btle.Peripheral(self._mac_address, self._addr_type, 0)
            self._connection = connection.withDelegate(self)

            char = self.received_characteristic
            handle = char.valHandle + 1
            msg = bytearray([0x01, 0x00])
            self._connection.writeCharacteristic(handle, msg)
        except RuntimeError as e:
            # TODO ronesim handle error
            print(e)
            return False

        return True

    def disconnect(self):
        """ Disconnect the device """
        try:
            self._connection.disconnect()
        except btle.BTLEException:
            pass

        self._connection = None

    @connection_required
    def turn_off(self):
        """ Turn off the light """
        msg = bytearray([0xCC, 0x24, 0x33])
        self.write_characteristic.write(msg)
        self._state = False

    @connection_required
    def turn_on(self):
        """ Turn on the light """
        msg = bytearray([0xCC, 0x23, 0x33])
        self.write_characteristic.write(msg)
        self._state = True

    @connection_required
    def set_color(self, color_list):
        """ 
         Change bulb's color
         :param color_list: color as a list of 3 values between 0 and 255 (red, green, blue)
        """
        self._color = color_list
        msg = bytearray([MAGIC_BLUE_SET_COLOR, self._color[0], self._color[1], self._color[2], int(
            self._intensity * 255), 0xF0, 0xAA])
        self.write_characteristic.write(msg)

    @connection_required
    def set_warm_light(self, intensity=1.0):
        """
        Intensity of color
        :param intensity: between 0.0 and 1.0
        """
        self._intensity = intensity
        msg = bytearray([MAGIC_BLUE_SET_COLOR, self._color[0], self._color[1], self._color[2], int(
            self._intensity * 255), 0x0F, 0xAA])
        self.write_characteristic.write(msg)

    @property
    def write_characteristic(self):
        """Get bluepy.btle characteristic for writing data"""
        characteristic = self._connection.getCharacteristics(
            uuid=UUID_CHARACTERISTIC_WRITE)
        if not characteristic:
            return None
        return characteristic[0]

    @property
    def received_characteristic(self):
        """Get bluepy.btle characteristic for receiving data"""
        characteristic = self._connection.getCharacteristics(
            uuid=UUID_CHARACTERISTIC_RECEIVED)
        if not characteristic:
            return None
        return characteristic[0]

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
    def color(self):
        return self._color

    @property
    def intensity(self):
        return self._intensity
