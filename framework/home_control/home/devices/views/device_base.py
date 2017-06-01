from abc import abstractmethod

from rest_framework.views import APIView


class DeviceBaseManager(APIView):
    @abstractmethod
    def initialize(self, device_id):
        """
        Function used for initializing database object and getting device object instance
        :param device_id: 
        :return: object from database, class object instance
        """
        pass

    @abstractmethod
    def get_object(self, device_id):
        """
        Get object from database
        :param device_id: 
        :return: object from database
        """
        pass

    @abstractmethod
    def get(self, request, device_id):
        """
        Get info about the device, using device id
        :param request: 
        :param device_id: 
        :return: Object serialized
        """
        pass

    @abstractmethod
    def post(self, request, device_id):
        """
        Use device features to change its state and attributes
        :param request: 
        :param device_id: 
        :return: 
        """
        pass

    def change_state(self, db_device, device, state):
        """ Change state (on/off) """
        if state == "on":
            # turn on
            device.turn_on()
            db_device.is_on = True
        else:
            # turn off
            device.turn_off()
            db_device.is_on = False
        db_device.save()
