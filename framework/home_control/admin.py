from django.contrib import admin

from .models import Room, Light, Plug, Thermostat, Lock, Webcam, Product

admin.site.register(Room)
admin.site.register(Light)
admin.site.register(Plug)
admin.site.register(Thermostat)
admin.site.register(Lock)
admin.site.register(Webcam)
admin.site.register(Product)
