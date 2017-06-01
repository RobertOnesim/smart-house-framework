from django.conf.urls import url

from . import views

urlpatterns = [
    # home/device/manage
    url(r'^manage/$', views.DeviceManager.as_view(), name='add_device'),

    # home/device/manage/<device_type>/<device_id>
    url(r'^manage/(?P<device_type>light|plug|thermostat|lock|webcam)/(?P<device_id>[0-9]+)/$',
        views.DeviceManager.as_view(), name="remove_device"),

    # urls for each type of devices
    # home/device/light/<device_id>
    url(r'light/(?P<device_id>[0-9]+)/$', views.LightManager.as_view(), name='get_light'),

    # home/device/light/<device_id>
    url(r'light/(?P<device_id>[0-9]+)/$', views.LightManager.as_view(), name='update_light'),

    # home/device/thermostat/<device_id>
    url(r'thermostat/(?P<device_id>[0-9]+)/$', views.ThermostatManager.as_view(), name='get_thermostat'),

    # home/device/thermostat/<device_id>
    url(r'thermostat/(?P<device_id>[0-9]+)/$', views.ThermostatManager.as_view(), name='update_thermostat'),

    # home/device/lock/<device_id>
    url(r'lock/(?P<device_id>[0-9]+)/$', views.LockManager.as_view(), name='get_lock'),

    # home/device/lock/<device_id>
    url(r'lock/(?P<device_id>[0-9]+)/$', views.LockManager.as_view(), name='update_lock'),

    # home/device/webcam/<device_id>
    url(r'webcam/(?P<device_id>[0-9]+)/$', views.WebcamManager.as_view(), name='get_webcam'),

    # home/device/webcam/<device_id>
    url(r'webcam/(?P<device_id>[0-9]+)/$', views.WebcamManager.as_view(), name='update_webcam'),

]
