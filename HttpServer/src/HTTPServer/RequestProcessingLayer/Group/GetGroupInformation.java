package HTTPServer.RequestProcessingLayer.Group;

import HTTPServer.BusinessLogicLayer.Entity.Member;
import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.GroupIDNotExistedException;
import HTTPServer.BusinessLogicLayer.Exception.MembershipNotExistedException;
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
import java.util.List;

/**
 * Created by 87485 on 2016/12/2.
 */
public class GetGroupInformation extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID=request.getParameter("user_ID");

        response.setCharacterEncoding("utf-8");
        response.setContentType("utf-8");

        UserList userList = new UserList();
        User user = null;
        try {
            user = userList.getUserByUserID(Long.parseLong(user_ID));
            List<Member> memberList = user.getGroupJoinedMemberList(Long.parseLong(request.getParameter("group_ID")));
            JSONObject jo=new JSONObject();
            JSONArray ja=new JSONArray();

            for (Member member : memberList) {
                ja.put(member.getUserID());
            }

            jo.put("code","0");
            jo.put("message","");
            jo.put("user_ID",user_ID);
            jo.put("GroupMemberList",ja);
            String jsonStr=jo.toString();

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
        } catch (MembershipNotExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"MembershipNotExistedException\"}";
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
        } catch (GroupIDNotExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"GroupIDNotExistedException\"}";
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
