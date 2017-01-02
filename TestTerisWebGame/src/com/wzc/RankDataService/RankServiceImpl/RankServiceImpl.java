package com.wzc.RankDataService.RankServiceImpl;

import com.wzc.Dao.UserDao;
import com.wzc.Entity.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 87485 on 2016/12/8.
 */
public class RankServiceImpl  {


    public String GetRank() {
        UserDao userDao=new UserDao();
        ArrayList<User> userlist=userDao.getScoreRank();
        JSONObject jo=new JSONObject();
        JSONArray ja=new JSONArray();

        try {
            jo.put("code","0");
            jo.put("message","");
            jo.put("length",userlist.size());

            for(User user:userlist){
                JSONObject jotemp=new JSONObject();
                jotemp.put("username",user.getUserName());
                jotemp.put("score",user.getScore());

                ja.put(jotemp);
                jotemp=null;
            }
            jo.put("users",ja);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }
}
