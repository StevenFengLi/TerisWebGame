package HTTPServer.RequestProcessingLayer.Group;

import HTTPServer.BusinessLogicLayer.Entity.Group;
import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.OutOfLengthException;
import HTTPServer.BusinessLogicLayer.Exception.UserIDNotExistedException;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 87485 on 2016/12/2.
 */
public class CreateGroup extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID_1=request.getParameter("user_ID_1");
        String user_ID_list=request.getParameter("user_ID_list");//[12,13,14]
        String[] array=user_ID_list.split(",");
        List<Long> list=new ArrayList<Long>();
        for(String str:array){
            long temp=Long.parseLong(str);
            list.add(temp);
        }
        UserList userlist=new UserList();
        User user=null;
        try {
            user=userlist.getUserByUserID(Long.parseLong(user_ID_1));
            Group group=user.createGroup("temp",list);
            JSONObject jo=new JSONObject();
            try {
                jo.put("code","0");
                jo.put("message","");
                jo.put("group_ID",String.valueOf(group.getGroupID()));
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
        } catch (OutOfLengthException e) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"OutOfLengthException\"}";
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


    }
}
