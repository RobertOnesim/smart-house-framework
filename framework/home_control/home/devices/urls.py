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
]
