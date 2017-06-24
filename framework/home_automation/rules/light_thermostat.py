from home_automation.devices import LightFactory
from home_control.models import HomeRule, Room


def temperature_to_rgb(temperature, roomId):
    global instance_light
    rule = HomeRule.objects.get(type="Light-Thermostat")
    if rule.is_set:
        # can apply the rule
        # compute red, green and blue values
        minimum, maximum = 0, 50
        ratio = 2 * (int(temperature) - minimum) / (maximum - minimum)
        b = int(max(0.0, 255 * (1 - ratio)))
        r = int(max(0.0, 255 * (ratio - 1)))
        g = 255 - b - r
        room = Room.objects.get(pk=roomId)
        # for each light in the room apply change colors
        for light in room.lights.all():
            light.color = str(r) + ' ' + str(g) + ' ' + str(b)
            instance_light = LightFactory.create_light_instance(light)
            instance_light.connect()
            instance_light.set_color([r, g, b])
            instance_light.disconnect()
            light.save()
