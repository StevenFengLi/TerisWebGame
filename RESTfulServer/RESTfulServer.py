#coding:utf8
from flask import Flask, jsonify, abort,json,request,render_template, url_for, request,redirect,make_response
from DataAccessLayer import ScoreDAO
from SOAPAccessLayer import ScoreSOAPAO

app = Flask(__name__)


@app.route('/TerisGame/RESTfulServer/GetAllScores_', methods=['GET'])
def get_all_scores_():
    local_score = ScoreDAO.ScoreDAO().get_local_score_list()
    #print  len(local_score)
    remote_score = ScoreSOAPAO.ScoreSOAPAO().get_remote_score_list()
    #print len(remote_score)
    all_score = local_score + remote_score

    for i in range(len(all_score) - 1):
        j = len(all_score) - 1
        while j > i:
            if all_score[j]["user_score"] > all_score[j - 1]["user_score"]:
                all_score[j], all_score[j - 1] = all_score[j - 1], all_score[j]
            j -= 1

    msg=all_score
    #print len(all_score)
    RankName="双平台排行榜"
    return render_template('rank.html', msg=msg,RankName=u'双平台排行榜')

@app.route('/TerisGame/RESTfulServer/GetLocalScoresSelf', methods=['GET'])
def get_local_scores_self():
    local_score = ScoreDAO.ScoreDAO().get_local_score_list()
    #print len(remote_score)


    for i in range(len(local_score) - 1):
        j = len(local_score) - 1
        while j > i:
            if local_score[j]["user_score"] > local_score[j - 1]["user_score"]:
                local_score[j], local_score[j - 1] = local_score[j - 1], local_score[j]
            j -= 1

    msg=local_score
    RankName="本地排行榜"
    return render_template('rank.html', msg=msg,RankName=u'本地排行榜')

@app.route('/TerisGame/RESTfulServer/GetLocalScores', methods=['GET'])
def get_local_scores():
    scorelist=ScoreDAO.ScoreDAO().get_local_score_list()
    users=dict()
    users["code"]="0"
    users["message"]=""
    users["users"]=scorelist
    return json.dumps(users)

@app.route('/TerisGame/RESTfulServer/UpdateLocalScore', methods=['POST'])
def update_local_score():
    user_name=request.form.get('username')
    user_score=request.form.get('score')
    print user_score
    if len(user_name) == 0 and user_score < 0:
        abort(404)
    user_name = user_name.encode("utf-8")
    ScoreDAO.ScoreDAO().update_score(user_name, user_score)

    return render_template('block.html',username=user_name,msg="Update Score successfully!")

@app.route('/TerisGame/RESTfulServer/AddUser',methods=['POST'])
def add_user():
    if request.method == 'POST':
        user_name=request.form.get('username')
        psw=request.form.get('password')
        user_name = user_name.encode("utf-8")
        psw = psw.encode("utf-8")
        if len(ScoreDAO.ScoreDAO().find_user(user_name))!=0:
            msg = "UserName exists"
            return render_template('regist.html',msg=msg)
        ScoreDAO.ScoreDAO().add_user(user_name,psw)
        rstr=" regist successfully!"
        msg=user_name+rstr
        return render_template('index.html',msg=msg)
@app.route('/TerisGame/RESTfulServer/FindUser',methods=['POST'])
def find_user():
    if request.method=='POST':
        user_name=request.form.get('username')
        psw=request.form.get('password')
        user_=ScoreDAO.ScoreDAO().find_user(user_name)
        if len(ScoreDAO.ScoreDAO().find_user(user_name)) == 0:
            return render_template('index.html')
        elif user_["user_password"]!= psw:
            return render_template('index.html')
    return render_template('block.html',username=user_name)

@app.route('/TerisGame/RESTfulServer/Index',methods=['GET'])
def index():
    return render_template('index.html')
@app.route('/TerisGame/RESTfulServer/Regist',methods=['GET'])
def regist():
    return render_template('regist.html')


if __name__ == '__main__':
    app.run(host='192.168.1.237',debug=True)
