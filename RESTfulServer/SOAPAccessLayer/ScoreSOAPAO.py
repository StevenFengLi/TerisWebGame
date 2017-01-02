from suds.client import Client
from flask import json


class ScoreSOAPAO(object):
    def __init__(self):
        #modify the uri
        self.client = Client('http://192.168.43.14:8080/TestTerisWebGame_war_exploded/services/RankServiceImpl?wsdl')

    def get_remote_score_list(self):
        jsonStr = self.client.service.GetRank()
        Jsondict = json.loads(jsonStr, encoding="utf-8");
        userslist = Jsondict["users"];#maybe error(bug)


        remote_score_list = []
        for user in userslist:
            remote_score = dict()
            remote_score['user_name'] = user["username"]
            remote_score['user_score'] = user["score"]
            remote_score_list.append(remote_score)

        return remote_score_list

