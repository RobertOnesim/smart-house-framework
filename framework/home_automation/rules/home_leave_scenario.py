from home_automation import FakeThermostat
from home_automation.devices import LightFactory
from home_control.models import Room, HomeRule


def home_leave_scenario(roomId):
    print("here")
    rule = HomeRule.objects.get(type="Leaving Home")
    if rule.is_set:
        room = Room.objects.get(pk=roomId)
        for light in room.lights.all():
            if light.is_on:
                instance_light = LightFactory.create_light_instance(light)
                instance_light.connect()
                instance_light.turn_off()
                instance_light.disconnect()

                light.is_on = False
                light.save()
        for therm in room.thermostats.all():
            if therm.is_on:
                instance_therm = FakeThermostat(therm.mac_address, therm.name, therm.is_on, therm.ip_address,
                                                therm.temperature, therm.humidity)
                instance_therm.connect()
                instance_therm.set_temperature(20)
                instance_therm.disconnect()

                therm.temperature = 20
                therm.save()
