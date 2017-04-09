from django.db import models


class Room(models.Model):
    name = models.CharField(max_length=150)
    number_of_devices = models.IntegerField(default=0)

    def __str__(self):
        return self.name + ': ' + str(self.number_of_devices)
