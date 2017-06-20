from home_automation.devices import FakeLock, FakeThermostat, FakeWebcam

# print(bytearray([0xCC, 0x24, 0x33]))
# print(0x24)
# print(0xCC)
# print(0x33)
# mb = magic_blue.MagicBlue("f8:1d:78:60:2a:5a","MagicBlue", True, "bla", 0.5)
# mb.connect()
# print(mb.is_connected())
# print(mb.name, mb.mac_address, mb.state)
# # print("turning off")
# # mb.turn_off()
# # print("turning on")
# # mb.turn_on()
# # print("change color")
# # print("change brithness")
# mb.set_color([25,178,102])
#
# mb.set_warm_light(0.5)
#
# mb.set_random_color()
#
# mb.disconnect()

fl = FakeLock("dsada", "FakeLock", True, 1235)

print(fl.connect())
print(fl.pin_code)
fl.change_pin_code(4356)
print(fl.pin_code)

ft = FakeThermostat("dsada", "FakeLock", True, 25, 23)

print(ft.connect())
print(ft.temperature, ft.humidity)
ft.set_temperature(46)
print(ft.temperature)

fw = FakeWebcam("DSAD", "FakeWebcam", True, True)
print(fw.night_vision)
