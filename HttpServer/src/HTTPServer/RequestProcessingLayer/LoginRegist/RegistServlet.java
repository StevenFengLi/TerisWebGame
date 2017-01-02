package HTTPServer.RequestProcessingLayer.LoginRegist;

import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.UserPhoneNumberExistedException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by 87485 on 2016/11/26.
 */
public class RegistServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String phone=request.getParameter("phone");
        String password=request.getParameter("password");
        String nickname=request.getParameter("nickname");

        User user=new User();

        UserList userlist=new UserList();

        try {
            user=userlist.addUser(phone,password,nickname);
        } catch (UserPhoneNumberExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"status\":\"user_exists\"}";
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write(jsonStr);
            } catch (IOException e2) {
                e2.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            e.printStackTrace();
        } catch (DataAccessLayerException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"status\":\"DataAccessLayerException\"}";
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write(jsonStr);
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        response.setCharacterEncoding("UTF-8");

        response.setContentType("application/json; charset=utf-8");

        long userID=user.getUserID();
        
        JSONObject jo=new JSONObject();
        try {
            jo.put("code","0");
            jo.put("status","regist_successful");
            jo.put("user_id",String.valueOf(userID));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String jsonStr=jo.toString();

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonStr);
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
