from django.conf.urls import url

from . import views

urlpatterns = [
    # home/rules/
    url(r'^$', views.HomeRuleList.as_view(), name="manage_room"),
]
