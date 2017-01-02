package HTTPServer.RequestProcessingLayer.Square;

import HTTPServer.BusinessLogicLayer.Entity.*;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.SquareMessageIDNotExistedException;
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
public class GetComments extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID = request.getParameter("user_ID");
        String square_message_ID = request.getParameter("square_message_ID");

        try {
            Square s = new Square();
            SquareMessage sm = s.getSquareMessageBySquareMessageID(Integer.parseInt(square_message_ID));
            List<Comment> commentList = sm.getCommentList();

            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();

            try {


                for (Comment comment : commentList) {
                    JSONObject jotemp = new JSONObject();
                    jotemp.put("comment_ID", comment.getCommentID());
                    jotemp.put("sender_user_ID", comment.getSenderID());
                    jotemp.put("comment_text", comment.getCommentText());
                    jotemp.put("comment_time", comment.getCommentTime());
                    jotemp.put("comment_Reply_ID", comment.getCommentRepliedID());

                    ja.put(jotemp);
                    jotemp = null;

                }

                jo.put("code", "0");
                jo.put("message", "");
                jo.put("comments", ja);

                String jsonStr = jo.toString();

                response.setCharacterEncoding("utf-8");
                response.setContentType("GBK");
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.write(jsonStr);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null)
                        out.close();
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
    }
}
