from django.conf.urls import include, url
from django.contrib import admin

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^home/', include('home_control.urls')),
    url(r'^api/', include('home_control.accounts.urls'), name='users-api'),
]
