package HTTPServer.RequestProcessingLayer.Friends;

import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by 87485 on 2016/12/1.
 */
public class AddFriend extends HttpServlet {
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID_1=request.getParameter("user_ID_1");
        String user_Phone_2=request.getParameter("user_Phone_2");

        UserList userList = new UserList();
        User user1;
        User user2;
        try {

            user1 = userList.getUserByUserID(Long.parseLong(user_ID_1));
            user2=userList.getUserByUserPhoneNumber(user_Phone_2);
            long user_ID_2=user2.getUserID();
            user1.addFriendByFriendUserID(user_ID_2);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            //String jsonStr = "{\"code\":\"0\",\"message\":\"\"}";
            JSONObject jo=new JSONObject();
            jo.put("code","0");
            jo.put("user_ID_2",user_ID_2);
            jo.put("message","");

            String jsonStr=jo.toString();

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

        } catch (UserIDNotExistedException e) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"UserIDNotExistedException\"}";
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

        } catch (DataAccessLayerException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"DataAccessLayerException\"}";
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
        } catch (FriendshipExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"FriendshipExistedException\"}";
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
        } catch (FriendUserIDSameAsMineException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"FriendUserIDSameAsMineException\"}";
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
        } catch (UserPhoneNumberNotExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"UserPhoneNumberNotExistedException\"}";
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
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
