from django.db import models


class Product(models.Model):
    name = models.CharField(max_length=150)
    type = models.CharField(max_length=100)
    connection_type = models.CharField(max_length=20)

    def __str__(self):
        return self.name + ': ' + self.type + ' ' + self.connection_type
