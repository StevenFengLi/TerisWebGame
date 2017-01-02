package com.wzc.utils;

import com.wzc.Dao.UserDao;
import com.wzc.Entity.User;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by 87485 on 2016/12/6.
 */
public class GetRankBy2Systems {

    public static GetRankBy2Systems get2sys=new GetRankBy2Systems();
    public String DoGetRanKBy2Systems() {
        UserDao userdao = new UserDao();
        ArrayList<User> arraylist = userdao.getScoreRank();

        String ulStr=null;

        try {
            if(1==1){
           String usersJsonStr = get2sys.DoGetRankService();

            JSONObject jo = new JSONObject(usersJsonStr);

            JSONArray ja = jo.getJSONArray("users");

            //JSONArray ja=new JSONArray("{\"code\":\"0\",\"length\":5,\"message\":\"\",\"users\":[{\"score\":1000,\"username\":\"2xiaoer6\"},{\"score\":900,\"username\":\"2xiaoer4\"},{\"score\":200,\"username\":\"2xiaoer3\"},{\"score\":100,\"username\":\"2xiaoer2\"},{\"score\":100,\"username\":\"2xiaoer5\"}]}");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jotemp = ja.getJSONObject(i);

                    User usertemp=new User();
                    //User usertemp = null;
                    usertemp.setUsername(jotemp.getString("user_name"));
                    usertemp.setScore(Integer.parseInt(jotemp.getString("user_score")));
                    arraylist.add(usertemp);
                    //System.out.println((i+1)+"\t"+jotemp.getString("username")+"\t"+jotemp.getString("score"));


                    usertemp = null;
                    jotemp = null;
                }
                Collections.sort(arraylist, new Comparator<User>() {
                    public int compare(User o1, User o2) {
                        if (o1.getScore() < o2.getScore())
                            return 1;
                        else if (o1.getScore() == o2.getScore()) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }

                });

            }
            JSONObject jo2 = new JSONObject();
            JSONArray ja2 = new JSONArray();


            jo2.put("code", "0");
            jo2.put("message", "");
            jo2.put("length", arraylist.size());

            for (User user : arraylist) {
                JSONObject jotemp2 = new JSONObject();
                jotemp2.put("username", user.getUserName());
                jotemp2.put("score", user.getScore());

                ja2.put(jotemp2);
                jotemp2 = null;
            }
            jo2.put("users", ja2);

            ulStr = jo2.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ulStr;
    }

    private static String DoGetRankService() throws JSONException {
        System.out.println("****查询所有getAllUsers****");

        JerseyClientBuilder cb=new JerseyClientBuilder();

        Client client = cb.newClient();

        //Client client=client1.build();
        WebTarget webTarget = client.target("http://192.168.1.237:5000/TerisGame");
        WebTarget resourceWebTarget = webTarget.path("RESTfulServer");
        WebTarget helloworldWebTarget = resourceWebTarget.path("GetLocalScores");

        Invocation.Builder invocationBuilder = helloworldWebTarget.request(MediaType.TEXT_PLAIN_TYPE);
        invocationBuilder.header("some-header", "true");

        Response response = invocationBuilder.get();
        System.out.println(response.getStatus());
        String usersJsonStr=response.readEntity(String.class);
        System.out.println(usersJsonStr);

        JSONObject jo=new JSONObject(usersJsonStr);
        //String code=jo.getString("code");
        //String message=jo.getString("message");
        JSONArray ja=jo.getJSONArray("users");
        System.out.println("RANK"+"\t"+"username"+"\t"+"score");
        for (int i=0;i<ja.length();i++) {
            JSONObject jotemp=ja.getJSONObject(i);
            System.out.println((i+1)+"\t"+jotemp.getString("user_name")+"\t"+jotemp.getString("user_score"));
            jotemp=null;
        }
        return usersJsonStr;

    }

}

