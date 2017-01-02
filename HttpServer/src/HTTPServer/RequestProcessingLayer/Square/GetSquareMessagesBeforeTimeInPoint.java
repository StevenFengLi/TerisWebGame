package HTTPServer.RequestProcessingLayer.Square;

/**
 * Created by 87485 on 2016/12/2.
 */
import HTTPServer.BusinessLogicLayer.Entity.Square;
import HTTPServer.BusinessLogicLayer.Entity.SquareMessage;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.sql.Timestamp;

import java.util.List;

/**
 * Created by 87485 on 2016/12/2.
 */
public class GetSquareMessagesBeforeTimeInPoint extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String timeStr = request.getParameter("time");

        Timestamp ts;
        ts = Timestamp.valueOf(timeStr);

        Square square = new Square();
        List<SquareMessage> squareMessageList = null;
        try {
            squareMessageList = square.getTenSquareMessagesBeforeTime(ts);
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();

            try {


                for (SquareMessage squareMessage : squareMessageList) {
                    JSONObject jotemp = new JSONObject();
                    jotemp.put("square_message_ID", squareMessage.getSquareMessageID());
                    jotemp.put("sender_user_ID", squareMessage.getSenderUserID());
                    jotemp.put("square_message_text", squareMessage.getSquareMessageText());
                    jotemp.put("square_message_time", squareMessage.getSquareMessageTime());

                    ja.put(jotemp);
                    jotemp=null;

                }

                jo.put("code", "0");
                jo.put("message", "");
                jo.put("square_messages", ja);

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
        }
    }
}

