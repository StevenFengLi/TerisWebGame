package com.wzc.loginServlet;

import com.wzc.Dao.UserDao;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 87485 on 2016/11/22.
 */
public class ScoreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String scores = request.getParameter("score");
        int score=Integer.parseInt(scores);

        UserDao u = new UserDao();
        u.addUserScore(username,score);
        request.setAttribute("msg3", "恭喜："+username+"，得分上传成功");
        request.setAttribute("username",username);
        request.getRequestDispatcher("/block.jsp").forward(request, response);

    }
}
