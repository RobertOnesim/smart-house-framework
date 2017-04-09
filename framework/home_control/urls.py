from django.conf.urls import url

from . import views

urlpatterns = [
    # /home_control/
    url(r'^$', views.RoomList.as_view(), name="index"),

    # /room/add
    url(r'^room/add/$', views.RoomList.as_view(), name="add_room"),
    # /room/manage/<room_id>
    url(r'^room/manage/(?P<room_id>[0-9]+)/$', views.RoomDetail.as_view(), name="manage_room"),

    # /device/add/
    url(r'^device/add/$', views.DeviceManager.as_view(), name="add_device"),

    # /home_control/room/<room_id>
    # url(r'^room/(?P<room_id>[0-9]+)/$', views.detail, name="detail"),
]
