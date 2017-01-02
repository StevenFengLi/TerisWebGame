package HTTPServer.RequestProcessingLayer.LoginRegist;
import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.DataAccessLayerException;
import HTTPServer.BusinessLogicLayer.Exception.UserPhoneNumberNotExistedException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 87485 on 2016/11/26.
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        UserList user_list = new UserList();
        User user = new User();

        try {
            user = user_list.getUserByUserPhoneNumber(phone);
        } catch (UserPhoneNumberNotExistedException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"status\":\"not_exists\"}";
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

        } catch (DataAccessLayerException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"status\":\"DataAccessLayerException\"}";
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
        }

        boolean val;

        val=user.validatePassword(password);
        if(val) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject jo=new JSONObject();

            try {
                jo.put("status","true");
                jo.put("user_id",String.valueOf(user.getUserID()));
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
        }
        if (!val) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"status\":\"wrong_password\"}";
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write(jsonStr);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }
}