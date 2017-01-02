package HTTPServer.RequestProcessingLayer.Square;

import HTTPServer.BusinessLogicLayer.Entity.Comment;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by 87485 on 2016/12/2.
 */
public class SendComment extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String sender_user_ID=request.getParameter("sender_user_ID");
        String square_message_ID=request.getParameter("square_message_ID");
        String comment_text=request.getParameter("comment_text");
        String comment_time=request.getParameter("comment_time");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserList userList=new UserList();

        try {
            User user=userList.getUserByUserID(Long.parseLong(sender_user_ID));
            Comment comment=user.sendComment(Long.parseLong(square_message_ID),comment_text,sdf.parse(comment_time));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            JSONObject jo=new JSONObject();
            jo.put("code","0");
            jo.put("message","0");
            jo.put("comment_ID",comment.getCommentID());

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

        } catch (OutOfLengthException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            String jsonStr="{\"code\":\"-1\",\"message\":\"OutOfLengthException\"}";
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

            String jsonStr="{\"code\":\"-1\",\"message\":\"DataAccessLayerException\"}";
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
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TimeAfterCurrentException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            String jsonStr="{\"code\":\"-1\",\"message\":\"TimeAfterCurrentException\"}";
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

            String jsonStr="{\"code\":\"-1\",\"message\":\"SquareMessageIDNotExistedException\"}";
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
