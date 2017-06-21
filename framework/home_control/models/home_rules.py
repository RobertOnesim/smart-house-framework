from django.db import models


class HomeRule(models.Model):
    type = models.CharField(max_length=50)
    description = models.CharField(max_length=150)
    is_set = models.BooleanField(default=False)

    def __str__(self):
        return self.type + ': ' + self.description
