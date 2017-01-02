import MySQLdb


class ScoreDAO(object):
    def __init__(self):
        self.conn = MySQLdb.connect(host="192.168.43.225",user="myuser",port=3306, passwd="FYwsLF1118!", db="game_user")

    def __del__(self):
        self.conn.close()

    def get_local_score_list(self):
        curs = self.conn.cursor()
        curs.execute("select * from users2;")
        rows = curs.fetchall()

        local_score_list = []
        for row in rows:
            local_score = dict()
            local_score["user_name"] = row[0]
            local_score["user_score"] = row[2]
            local_score_list.append(local_score)
        return local_score_list

    def add_user(self, user_name,password):
        curs = self.conn.cursor()
        curs.execute("insert into users2 (username, psw,score) values ('" + user_name + "','" + password + "', " + str(0) + ");")
        self.conn.commit()

    def update_score(self,user_name,user_score):
        print(user_score);
        curs=self.conn.cursor()
        curs.execute("update users2 set score=('"+user_score+"') WHERE username=('"+user_name+"'); ")
        self.conn.commit()

    def find_user(self,user_name):
        curs=self.conn.cursor()
        curs.execute("select * from users2 where username=('"+str(user_name)+"');")
        f_row=curs.fetchall();
        print f_row
        user_ = dict()
        if len(f_row)!=0:
            row = f_row[0]
            user_["user_name"]=row[0];
            user_["user_password"]=row[1];
        return user_


