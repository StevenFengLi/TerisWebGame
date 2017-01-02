package HTTPServer.RequestProcessingLayer.UserInformation;

import HTTPServer.BusinessLogicLayer.Entity.User;
import HTTPServer.BusinessLogicLayer.Entity.UserList;
import HTTPServer.BusinessLogicLayer.Exception.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static HTTPServer.BusinessLogicLayer.Entity.User.UserUserSex.USER_USER_SEX_FEMALE;
import static HTTPServer.BusinessLogicLayer.Entity.User.UserUserSex.USER_USER_SEX_MALE;
import static HTTPServer.BusinessLogicLayer.Entity.User.UserUserSex.USER_USER_SEX_UNKNOWN;

/**
 * Created by 87485 on 2016/12/1.
 */
public class SetUserInformation extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String user_ID=request.getParameter("user_ID");
        String user_nickname=request.getParameter("user_nickname");
        String user_phone_number=request.getParameter("user_phone_number");
        String user_head_show=request.getParameter("user_head_show");
        String user_birthday=request.getParameter("user_birthday");
        String user_sex=request.getParameter("user_sex");
        String user_area_province=request.getParameter("user_area_province");
        String user_area_city=request.getParameter("user_area_city");
        String user_description=request.getParameter("user_description");


        UserList user_list=new UserList();

        User user=null;
        try {
            user=user_list.getUserByUserID(Integer.parseInt(user_ID));
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

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            User.UserUserSex jk=null;
            if(user_sex!=null) {
                switch (user_sex) {
                    case "USER_USER_SEX_FEMALE":
                        jk = USER_USER_SEX_FEMALE;
                        break;
                    case "USER_USER_SEX_MALE":
                        jk = USER_USER_SEX_MALE;
                        break;
                    case "USER_USER_SEX_UNKNOWN":
                        jk = USER_USER_SEX_UNKNOWN;
                        break;
                }
            }


            if(user_nickname!=null){
                user.updateUserNickname(user_nickname);
            }
            if(user_phone_number!=null) {
                user.updateUserPhoneNumber(user_phone_number);
            }
            if(user_head_show!=null) {
                user.updateUserHeadShow();
            }
            if(user_birthday!=null) {
                user.updateUserBirthday(sdf.parse(user_birthday));//sdf.format(sdf.parse(date));
            }
            if(jk!=null) {
                user.updateUserSex(jk);
            }
            if(user_area_province!=null) {
                user.updateUserAreaProvince(user_area_province);
            }
            if(user_area_city!=null) {
                user.updateUserAreaCity(user_area_city);
            }
            if(user_description!=null) {
                user.updateUserDescription(user_description);
            }

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"0\",\"message\":\"\"}";
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
            String jsonStr = "{\"code\":\"-1\",\"message\":\"OutOfLengthException\"}";
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
        } catch (TimeAfterCurrentException e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String jsonStr = "{\"code\":\"-1\",\"message\":\"TimeAfterCurrentException\"}";
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
        } catch (FormatException e) {
            e.printStackTrace();
        }
    }
}
