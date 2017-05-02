from django.conf.urls import url, include

from . import views

urlpatterns = [
    # /home/
    url(r'^$', views.RoomList.as_view(), name="index"),

    # home/room/add
    url(r'^room/add/$', views.RoomList.as_view(), name="add_room"),
    # home/room/manage/<room_id>
    url(r'^room/manage/(?P<room_id>[0-9]+)/$', views.RoomDetail.as_view(), name="manage_room"),

    # home/device/
    url(r'^device/', include('home_control.devices.urls'), name="device"),
]
