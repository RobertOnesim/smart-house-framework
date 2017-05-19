from django.conf.urls import url

from . import views

urlpatterns = [
    # home/device/add/
    url(r'^add/$', views.DeviceManager.as_view(), name="add_device"),

]
