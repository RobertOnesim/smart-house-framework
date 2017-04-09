from django.conf.urls import url

from .views import UserCreateAPIView, UserLoginAPIView

urlpatterns = [
    # api/register/
    url(r'^register/$', UserCreateAPIView.as_view(), name="register"),
    # api/login/
    url(r'^login/$', UserLoginAPIView.as_view(), name="login"),
]
