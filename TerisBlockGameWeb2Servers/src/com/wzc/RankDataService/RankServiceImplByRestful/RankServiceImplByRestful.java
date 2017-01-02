package com.wzc.RankDataService.RankServiceImplByRestful;

import com.wzc.Dao.UserDao;
import com.wzc.Entity.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/GetRank")
/**
 * Created by 87485 on 2016/12/8.
 */
public class RankServiceImplByRestful {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
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
