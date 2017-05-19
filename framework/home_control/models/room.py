from django.db import models
from django.utils import timezone


class Room(models.Model):
    name = models.CharField(max_length=150)
    number_of_devices = models.IntegerField(default=0)
    info = models.CharField(max_length=350, default="No action")
    date_update = models.DateTimeField(default=timezone.now, blank=True)

    def __str__(self):
        return self.name + ': ' + str(self.number_of_devices)
