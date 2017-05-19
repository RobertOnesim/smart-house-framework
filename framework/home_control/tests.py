from django.contrib.auth.models import User
from django.urls import reverse
from rest_framework import status
from rest_framework.test import APITestCase

from .models import Room, Light


class UserLoginAPIViewTests(APITestCase):
    def test_login_user(self):
        user = User.objects.create(username='testuser')
        user.set_password('12345')
        user.save()

        logged_in = self.client.login(username='testuser', password='12345')
        self.assertTrue(logged_in)


class RoomListTests(APITestCase):
    def setUp(self):
        self.user = User.objects.create(username='testuser')
        self.user.set_password('12345')
        self.user.save()
        self.logged_in = self.client.login(username='testuser', password='12345')

    def test_add_room(self):
        self.assertTrue(self.logged_in)

        url = reverse('add_room')
        data = {'name': 'LivingRoom',
                'number_of_devices': 1,
                'lights': []}
        response = self.client.post(url, data, format='json')

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Room.objects.count(), 1)

    def test_get_room_list(self):
        self.assertTrue(self.logged_in)
        url = reverse('index')
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)


class DeviceTests(APITestCase):
    def setUp(self):
        self.user = User.objects.create(username='testuser')
        self.user.set_password('12345')
        self.user.save()
        self.logged_in = self.client.login(username='testuser', password='12345')

    def test_add_device(self):
        self.assertTrue(self.logged_in)
        # add a new room
        room = Room(name='LivingRoom', number_of_devices=1)
        room.save()
        # add a new device (light)
        url = reverse('add_device')
        data = {'type': 'light',
                'info': {
                    'room': 1,
                    'name': 'MagicBlue',
                    'mac_address': 'f8:1d:78:60:2a:5a',
                    'is_on': True,
                    'color': "123 213 121",
                    'intensity': 0.5
                }
                }
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Light.objects.count(), 1)
