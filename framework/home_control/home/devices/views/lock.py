from django.http import Http404
from django.utils import timezone
from rest_framework import status
from rest_framework.response import Response

from home_automation import FakeLock
from home_control.home.devices import LockSerializer
from home_control.models import Lock
from .device_base import DeviceBaseManager


class LockManager(DeviceBaseManager):
    def get_object(self, device_id):
        try:
            return Lock.objects.get(pk=device_id)
        except Lock.DoesNotExist:
            raise Http404

    def initialize(self, device_id):
        db_lock = self.get_object(device_id)
        return db_lock, FakeLock(db_lock.mac_address, db_lock.name, db_lock.is_on, db_lock.ip_address, db_lock.pin_code)

    def get(self, request, device_id):
        db_lock = self.get_object(device_id)
        serializer = LockSerializer(db_lock)
        return Response(serializer.data)

    def post(self, request, device_id):
        db_lock, lock = self.initialize(device_id)
        action_type = request.data['action']

        # check action type
        changed = False
        if lock.connect():
            if action_type == 'state':
                # change state (on/off)
                db_lock.room.info = "Lock (" + db_lock.name + ") state was changed."
                self.change_state(db_lock, lock, request.data['state'])
                changed = True
            elif action_type == 'pin':
                # change lock pin code
                db_lock.room.info = "Lock (" + db_lock.name + ") pin code was changed."
                self.change_pin(db_lock, lock, request.data['pin'])
                changed = True
            if changed:
                db_lock.room.date_update = timezone.now()
                db_lock.room.save()
                return Response(status=status.HTTP_200_OK)
        return Response(status=status.HTTP_406_NOT_ACCEPTABLE)

    def change_pin(self, db_lock, lock, pin_code):
        """ Change thermostat temperature """
        if db_lock.is_on:
            lock.change_pin_code(pin_code)

            # store new info in the database
            db_lock.pin_code = pin_code
            db_lock.save()
            lock.disconnect()
