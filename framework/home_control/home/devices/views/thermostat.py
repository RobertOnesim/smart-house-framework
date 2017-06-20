from django.http import Http404
from django.utils import timezone
from rest_framework import status
from rest_framework.response import Response

from home_automation import FakeThermostat
from home_control.home.devices import ThermostatSerializer
from home_control.models import Thermostat
from .device_base import DeviceBaseManager


class ThermostatManager(DeviceBaseManager):
    def get_object(self, device_id):
        try:
            return Thermostat.objects.get(pk=device_id)
        except Thermostat.DoesNotExist:
            raise Http404

    def initialize(self, device_id):
        db_therm = self.get_object(device_id)
        return db_therm, FakeThermostat(db_therm.mac_address, db_therm.name, db_therm.is_on, db_therm.temperature,
                                        db_therm.humidity)

    def get(self, request, device_id):
        db_therm = self.get_object(device_id)
        serializer = ThermostatSerializer(db_therm)
        return Response(serializer.data)

    def post(self, request, device_id):
        db_therm, thermostat = self.initialize(device_id)
        action_type = request.data['action']

        # check action type
        changed = False
        if thermostat.connect():
            if action_type == 'state':
                # change state (on/off)
                db_therm.room.info = "Thermostat (" + db_therm.name + ") state was changed."
                self.change_state(db_therm, thermostat, request.data['state'])
                changed = True
            elif action_type == 'temperature':
                # change thermostat temperature
                db_therm.room.info = "Thermostat (" + db_therm.name + ") temperature was changed."
                self.change_temperature(db_therm, thermostat, request.data['temperature'])
                changed = True
            elif action_type == 'humidity':
                # change thermostat humidity
                db_therm.room.info = "Thermostat (" + db_therm.name + ") humidity was changed."
                self.change_humidity(db_therm, thermostat, request.data['humidity'])
                changed = True
            if changed:
                db_therm.room.date_update = timezone.now()
                db_therm.room.save()
                return Response(status=status.HTTP_200_OK)
        return Response(status=status.HTTP_406_NOT_ACCEPTABLE)

    def change_temperature(self, db_therm, thermostat, temperature):
        """ Change thermostat temperature """
        if db_therm.is_on:
            thermostat.set_temperature(temperature)

            # store new info in the database
            db_therm.temperature = temperature
            db_therm.save()
            thermostat.disconnect()

    def change_humidity(self, db_therm, thermostat, humidity):
        """ Change thermostat humidity """
        if db_therm.is_on:
            thermostat.set_humidity(humidity)

            # store new info in the database
            db_therm.humidity = humidity
            db_therm.save()
            thermostat.disconnect()
