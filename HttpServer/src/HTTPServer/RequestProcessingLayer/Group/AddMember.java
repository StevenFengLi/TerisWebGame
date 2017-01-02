package HTTPServer.RequestProcessingLayer.Group;

import HTTPServer.BusinessLogicLayer.Entity.Group;
import HTTPServer.BusinessLogicLayer.Entity.GroupList;
import HTTPServer.BusinessLogicLayer.Entity.Member;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.GroupIDNotExistedException;
import HTTPServer.BusinessLogicLayer.Exception.MembershipExistedException;
import HTTPServer.BusinessLogicLayer.Exception.UserIDNotExistedException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static HTTPServer.BusinessLogicLayer.Entity.Member.MemberRole.MEMBER_ROLE_COMMON;

/**
 * Created by 87485 on 2016/12/2.
 */
public class AddMember extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID=request.getParameter("user_ID");
        String group_ID=request.getParameter("group_ID");

        Member.MemberRole mr=MEMBER_ROLE_COMMON;

        GroupList grouplist=new GroupList();
        try {
            Group group=grouplist.getGroupByGroupID(Long.parseLong(group_ID));
            group.addMemberByMemberUserID(Long.parseLong(user_ID),mr);
            String jsonStr="{\"code\":\" 0\",\"message\":\"\"}";
            PrintWriter out=null;
            try {
                out=response.getWriter();
                out.write(jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(out!=null){
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
        } catch (MembershipExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"MembershipExistedException\"}";
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
