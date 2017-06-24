# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2017-06-24 12:35
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ('home_control', '0008_auto_20170620_2333'),
    ]

    operations = [
        migrations.AddField(
            model_name='light',
            name='ip_address',
            field=models.CharField(default='0.0.0.0', max_length=20),
        ),
        migrations.AddField(
            model_name='lock',
            name='ip_address',
            field=models.CharField(default='0.0.0.0', max_length=20),
        ),
        migrations.AddField(
            model_name='plug',
            name='ip_address',
            field=models.CharField(default='0.0.0.0', max_length=20),
        ),
        migrations.AddField(
            model_name='thermostat',
            name='ip_address',
            field=models.CharField(default='0.0.0.0', max_length=20),
        ),
        migrations.AddField(
            model_name='webcam',
            name='ip_address',
            field=models.CharField(default='0.0.0.0', max_length=20),
        ),
    ]
