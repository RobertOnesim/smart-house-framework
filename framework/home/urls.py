from django.conf.urls import url
from . import views

urlpatterns = [
    # /home/
    url(r'^$', views.RoomList.as_view(), name="index"),

    # /home/room/<room_id>
    # url(r'^room/(?P<room_id>[0-9]+)/$', views.detail, name="detail"),
]