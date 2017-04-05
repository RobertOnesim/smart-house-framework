from django.db import models


class Room(models.Model):
    name = models.CharField(max_length=150)
    number_of_devices = models.IntegerField(default=0)

    def __str__(self):
        return self.name + ': ' + str(self.number_of_devices)


class Light(models.Model):
    room = models.ForeignKey(Room, on_delete=models.CASCADE)
    name = models.CharField(max_length=200)
    mac_address = models.CharField(max_length=100)
    is_connected = models.BooleanField(default=False)

    def __str__(self):
        return self.name + ': ' + self.mac_address
