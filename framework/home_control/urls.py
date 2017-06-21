from django.conf.urls import url, include

from home_control.home.room import views

urlpatterns = [
    # /home/
    url(r'^$', views.RoomList.as_view(), name="index"),
    # home/room/
    url(r'^room/', include('home_control.home.room.urls'), name="room"),
    # home/device/
    url(r'^device/', include('home_control.home.devices.urls'), name="device"),
    # home/rules/
    url(r'^rules/', include('home_control.home.rules.urls'), name="rules"),
]
