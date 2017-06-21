from rest_framework import status, generics
from rest_framework.response import Response

from home_control.models import HomeRule
from .serializers import HomeRuleSerializer


class HomeRuleList(generics.ListCreateAPIView):
    queryset = HomeRule.objects.all()
    serializer_class = HomeRuleSerializer

    def post(self, request, *args, **kwargs):
        home_rule_id_list = request.data.getlist('homeRulesIDs')
        results = list(map(int, home_rule_id_list))
        home_rule_db = HomeRule.objects.all()
        for home_rule in home_rule_db:
            if home_rule.id in results:
                home_rule.is_set = True
            else:
                home_rule.is_set = False
            home_rule.save()

        return Response(status=status.HTTP_200_OK)
