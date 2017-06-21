from rest_framework import serializers

from home_control.models import HomeRule


class HomeRuleSerializer(serializers.ModelSerializer):
    class Meta:
        model = HomeRule
        fields = '__all__'
