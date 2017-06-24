from home_automation import FakeThermostat
from home_automation.devices import LightFactory
from home_control.models import Room, HomeRule


def home_arrive_scenario(roomId):
    rule = HomeRule.objects.get(type="Arriving Home")
    if rule.is_set:
        room = Room.objects.get(pk=roomId)
        for light in room.lights.all():
            if not light.is_on:
                instance_light = LightFactory.create_light_instance(light)
                instance_light.connect()
                instance_light.turn_on()
                instance_light.disconnect()

                light.is_on = True
                light.save()
        for therm in room.thermostats.all():
            if therm.is_on:
                instance_therm = FakeThermostat(therm.mac_address, therm.name, therm.is_on, therm.ip_address,
                                                therm.temperature, therm.humidity)
                instance_therm.connect()
                instance_therm.set_temperature(25)
                instance_therm.disconnect()

                therm.temperature = 25
                therm.save()
