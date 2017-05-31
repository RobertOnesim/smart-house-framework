from django.db import models

from .room import Room


class Device(models.Model):
    room = models.ForeignKey(Room, related_name="%(class)ss", on_delete=models.CASCADE)
    mac_address = models.CharField(max_length=100)
    name = models.CharField(max_length=200)
    is_on = models.BooleanField(default=False)

    class Meta:
        abstract = True

    def __str__(self):
        return self.name + ': ' + self.mac_address


class Light(Device):
    color = models.CharField(max_length=15)
    intensity = models.FloatField(default=1.0)


class Plug(Device):
    away_mode = models.BooleanField(default=False)
    schedule = models.CharField(max_length=1000)


class Lock(Device):
    pin_code = models.IntegerField(default=1234)
    voice_activation = models.BooleanField(default=False)


class Thermostat(Device):
    temperature = models.IntegerField()
    humidity = models.IntegerField()


class Webcam(Device):
    night_vision = models.BooleanField(default=False)
    motion_detection = models.BooleanField(default=True)
