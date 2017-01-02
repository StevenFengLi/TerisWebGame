package HTTPServer.RequestProcessingLayer;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by 87485 on 2016/12/8.
 */
public class test extends HttpServlet {
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        String jsonStr="{\"code\":\"-1\",\"message\":\"hahahha\",\"Users\":[{\"username\":\"lifeng\",\"score\":\"100\"},{\"username\":\"lifs\",\"score\":\"80\"}]}";
        PrintWriter out=null;
        try {
            JSONObject jo=new JSONObject(jsonStr);
            out=response.getWriter();
            out.write(jo.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null)
            {
                out.close();
            }
        }


    }
}
