package com.wzc.loginServlet;

/**
 * Created by 87485 on 2016/12/17.
 */

import com.wzc.Dao.UserDao;
import com.wzc.Entity.User;
import com.wzc.utils.GetRankBy2Systems;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class RankLocalServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("utf-8");


        UserDao userdao = new UserDao();
        ArrayList<User> arraylist = userdao.getScoreRank();

        String ulStr=null;
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();


        try {
            jo.put("code", "0");
            jo.put("message", "");
            jo.put("length", arraylist.size());

            for (User user : arraylist) {
                JSONObject jotemp = new JSONObject();
                jotemp.put("username", user.getUserName());
                jotemp.put("score", user.getScore());

                ja.put(jotemp);
                jotemp = null;
            }
            jo.put("users", ja);

            ulStr = jo.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }



        request.setAttribute("userlistStr", ulStr);
        request.setAttribute("RankName", "本地排行榜");
        request.getRequestDispatcher("/Ranks.jsp").forward(request, response);


    }
}

