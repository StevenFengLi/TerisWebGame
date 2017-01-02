package HTTPServer.RequestProcessingLayer.Friends;

import HTTPServer.BusinessLogicLayer.Entity.Friend;
import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.UserIDNotExistedException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 87485 on 2016/12/1.
 */
public class GetFriends extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID=request.getParameter("user_ID");
        JSONObject jo=new JSONObject();
        JSONArray ja=new JSONArray();
        UserList user_list=new UserList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            User user=user_list.getUserByUserID(Long.parseLong(user_ID));
            List<Friend> Friendlist=user.getFriendList();
            //Map<String,String> map=new HashMap<>();

            for (Friend friend:Friendlist)
            {
                JSONObject jotemp=new JSONObject();
                jotemp.put("user_ID",friend.getUserID());
                jotemp.put("user_nickname",friend.getUserNickname());
                jotemp.put("user_phone_number",friend.getUserPhoneNumber());
                jotemp.put("user_head_show",friend.getUserHeadShowURL());
                jotemp.put("user_birthday",sdf.format(friend.getUserBirthday()));
                jotemp.put("user_sex",friend.getUserSex().name());
                jotemp.put("user_area_province",friend.getUserAreaProvince());
                jotemp.put("user_area_city",friend.getUserAreaCity());
                jotemp.put("user_description",friend.getUserDescription());

                ja.put(jotemp);
                jotemp=null;
            }

            jo.put("code",0);
            jo.put("friends",ja);

            String jsonStr=jo.toString();

            response.setCharacterEncoding("utf-8");
            response.setContentType("GBK");
            PrintWriter out=null;
            try {
                out=response.getWriter();
                out.write(jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(out!=null)
                    out.close();
            }

        } catch (UserIDNotExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            String jsonStr="{\"code\":\"-1\",\"message\":\"UserIDNotExistedException\"}";
            PrintWriter out1 = null;

            try {
                out1 = response.getWriter();
                out1.write(jsonStr);
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (out1 != null) {
                    out1.close();
                }
            }
        } catch (DataAccessLayerException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            String jsonStr="{\"code\":\"-1\",\"message\":\"DataAccessLayerException\"}";
            PrintWriter out2 = null;

            try {
                out2 = response.getWriter();
                out2.write(jsonStr);
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (out2 != null) {
                    out2.close();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
