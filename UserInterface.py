import requests
import json
import time


class UserInterface:
    def __init__(self):
        self.Id = None
        self.front_page_url = "http://127.0.0.1:8080/"
        self.wiki_relations_url = "http://127.0.0.1:8080/wiki_relations/"

        self.getId()

    def getId(self):
        response = requests.get(self.front_page_url)
        json_response = response.json()
        self.Id = json_response

    def send_query(self, query):
        user_dict = {"id": self.Id, "query": query}
        user_json = json.dumps(user_dict)
        response = requests.post(self.wiki_relations_url, json=user_dict)
        print(response)

    def get_result(self):
        user_dict = {"id": self.Id, "query": None}
        while True:
            response = requests.get(self.wiki_relations_url, json=user_dict)
            if response.status_code == 204:
                time.sleep(10)
                continue
            json_response = response.json()
            if type(json_response) == list:
                for i in range(len(json_response)):
                    for key in json_response[i].keys():
                        print("{}. {} >> {}".format((i + 1), key, json_response[i][key]))
                break

    def enter_query(self):
        query = input("Please enter a query >> ")
        self.send_query(query)
        self.get_result()


userInterface = UserInterface()
userInterface.enter_query()
