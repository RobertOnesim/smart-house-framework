# from bluepy.btle import Scanner, DefaultDelegate
#
#
# def magic_blue():
#     scan_time = 100
#     last_scan = ScanDelegate()
#     scanner = Scanner().withDelegate(last_scan)
#     scanner.scan(scan_time)
#
#
# class ScanDelegate(DefaultDelegate):
#     def __init__(self):
#         DefaultDelegate.__init__(self)
#         self.devices = []
#
#     def handleDiscovery(self, dev, is_new_device, is_new_data):
#         if is_new_device:
#             self.devices.append(dev)
#             raw_name = dev.getValueText(9)
#             dev_name = raw_name.split('\x00')[0] if raw_name else "NO_NAME"
#             print('{: <5} {: <30} {: <12}'.format(len(self.devices),
#                                                   dev_name,
#                                                   dev.addr))
#
# magic_blue()
