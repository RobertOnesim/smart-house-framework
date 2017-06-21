from django.conf.urls import url
from rest_framework_jwt.views import obtain_jwt_token

from .views import UserCreateAPIView, UserLoginAPIView

urlpatterns = [
    # api/register/
    url(r'^register/$', UserCreateAPIView.as_view(), name="register"),
    # api/login/
    url(r'^login/$', UserLoginAPIView.as_view(), name="login"),
    # api/auth/token
    url(r'^auth/token/$', obtain_jwt_token),

]
