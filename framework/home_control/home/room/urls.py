from django.conf.urls import url

from . import views

urlpatterns = [
    # home/room/add
    url(r'^add/$', views.RoomList.as_view(), name="add_room"),
    # home/room/manage/<room_id>
    url(r'^manage/(?P<room_id>[0-9]+)/$', views.RoomDetail.as_view(), name="manage_room"),
]
