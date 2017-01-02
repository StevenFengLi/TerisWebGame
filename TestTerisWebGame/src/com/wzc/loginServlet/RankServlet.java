package com.wzc.loginServlet;

import com.wzc.utils.GetRankBy2Systems;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 87485 on 2016/12/11.
 */
public class RankServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("utf-8");

        GetRankBy2Systems get2systems=new GetRankBy2Systems();
        String ulStr=get2systems.DoGetRanKBy2Systems();


        request.setAttribute("userlistStr", ulStr);
        request.setAttribute("RankName", "双平台排行榜");
        request.getRequestDispatcher("/Ranks.jsp").forward(request, response);


    }
}
