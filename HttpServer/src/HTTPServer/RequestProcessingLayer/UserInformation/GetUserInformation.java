package HTTPServer.RequestProcessingLayer.UserInformation;

import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.UserIDNotExistedException;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;



/**
 * Created by 87485 on 2016/12/1.
 */
public class GetUserInformation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID=request.getParameter("user_ID");
        UserList userlist=new UserList();
        User user=new User();
        try {
            user=userlist.getUserByUserID(Long.parseLong(user_ID));
        } catch (UserIDNotExistedException e) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");

            String jsonStr="{\"code\":\"-1\",\"message\":\"UserIDNotExistedException\"}";
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
            
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=GBK");
        PrintWriter out=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject jo=new JSONObject();

        try {
            jo.put("code","0");
            jo.put("message","");
            jo.put("user_nickname",user.getUserNickname());
            jo.put("user_phone_number",user.getUserPhoneNumber());
            jo.put("user_head_show",user.getUserHeadShowURL());
            jo.put("user_birthday",sdf.format(user.getUserBirthday()));
            User.UserUserSex jk=user.getUserSex();
            jo.put("user_sex", jk.name());
            jo.put("user_area_province",user.getUserAreaProvince());
            jo.put("user_area_city",user.getUserAreaCity());
            jo.put("user_description",user.getUserDescription());
            String jsonStr=jo.toString();
            out=response.getWriter();
            out.write(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null)
                out.close();
        }
    }
}
