# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2017-05-31 21:22
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):
    dependencies = [
        ('home_control', '0004_auto_20170531_2122'),
    ]

    operations = [
        migrations.AlterField(
            model_name='lock',
            name='pin_code',
            field=models.IntegerField(default=1234),
        ),
    ]
