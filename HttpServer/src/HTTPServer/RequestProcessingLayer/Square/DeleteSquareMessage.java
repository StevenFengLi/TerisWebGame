package HTTPServer.RequestProcessingLayer.Square;

import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.SquareMessageIDNotExistedException;
import HTTPServer.BusinessLogicLayer.Exception.SquareMessageSenderNotMatchedException;
import HTTPServer.BusinessLogicLayer.Exception.UserIDNotExistedException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by 87485 on 2016/12/2.
 */
public class DeleteSquareMessage extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID=request.getParameter("user_ID");
        String square_message_ID=request.getParameter("square_message_ID");

        UserList userlist=new UserList();
        try {
            User user=userlist.getUserByUserID(Long.parseLong(user_ID));
            user.deleteSquareMessageBySquareMessageID(Long.parseLong(square_message_ID));
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
        } catch (SquareMessageSenderNotMatchedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"SquareMessageSenderNotMatchedException\"}";
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
        } catch (SquareMessageIDNotExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"SquareMessageIDNotExistedException\"}";
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
