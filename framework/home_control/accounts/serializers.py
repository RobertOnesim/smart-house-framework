from django.contrib.auth import get_user_model
from django.db.models import Q

from rest_framework.exceptions import ValidationError

from rest_framework.serializers import (
    CharField,
    EmailField,
    ModelSerializer
)

User = get_user_model()


class UserCreateSerializer(ModelSerializer):
    email = EmailField(label="Email Address")
    email2 = EmailField(label="Confirm Email")

    class Meta:
        model = User
        fields = ['username',
                  'email',
                  'email2',
                  'password'
                  ]
        extra_kwargs = {"password": {"write_only": True}
                        }

    def validate(self, data):
        return data

    def validate_email2(self, value):
        # check emails match
        data = self.get_initial()
        email1 = data.get("email")
        email2 = value
        if email1 != email2:
            raise ValidationError("Emails must match.")

        # check email exists
        user_qs = User.objects.filter(email=email1)
        if user_qs.exists():
            raise ValidationError("This user has already been registered")
        return value

    def create(self, validated_data):
        username = validated_data['username']
        email = validated_data['email']
        password = validated_data['password']
        user_obj = User(
            username=username,
            email=email
        )
        user_obj.set_password(password)
        user_obj.save()
        return validated_data


class UserLoginSerializer(ModelSerializer):
    token = CharField(allow_blank=True, read_only=True)
    username = CharField(required=False, allow_blank=True)
    email = EmailField(label='Email Address', required=False, allow_blank=True)

    class Meta:
        model = User
        fields = ['username',
                  'email',
                  'password',
                  'token'
                  ]
        extra_kwargs = {"password": {"write_only": True}
                        }

    def validate(self, data):
        email = data.get("email", None)
        username = data.get("username", None)
        password = data["password"]
        if not username and not email:
            raise ValidationError("A username or email is required to login.")

        user = User.objects.filter(
            Q(email=email) |
            Q(username=username)
        ).distinct()
        user = user.exclude(email__isnull=True).exclude(email__iexact='')
        if user.exists() and user.count() == 1:
            user_obj = user.first()
        else:
            raise ValidationError("This username/email is not valid.")

        if user_obj:
            if not user_obj.check_password(password):
                raise ValidationError("Incorrect credentials, please try again.")

        data["token"] = "SOME RANDOM TOKEN"  # TODO CHANGE

        return data
