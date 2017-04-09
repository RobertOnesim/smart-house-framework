from django.urls import reverse
from rest_framework import status
from rest_framework.test import APITestCase

from .models import Room, Light


class RoomListTests(APITestCase):
    def test_add_room(self):
        url = reverse('add_room')
        data = {'name': 'LivingRoom',
                'number_of_devices': 1,
                'lights': []}
        response = self.client.post(url, data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Room.objects.count(), 1)

    def test_get_room_list(self):
        url = reverse('index')
        response = self.client.get(url)
        self.assertEqual(response.status_code, status.HTTP_200_OK)


class DeviceTests(APITestCase):
    def test_add_device(self):
        # add a new room
        room = Room(name='LivingRoom', number_of_devices=1)
        room.save()
        print(room.id)
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
