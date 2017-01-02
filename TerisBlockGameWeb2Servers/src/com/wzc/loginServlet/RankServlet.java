package com.wzc.loginServlet;

import com.wzc.RankDataService.GetRankService.GetRankServiceBy2Systems;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 87485 on 2016/12/6.
 */
public class RankServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("utf-8");

        GetRankServiceBy2Systems get2systems=new GetRankServiceBy2Systems();
        String ulStr=get2systems.DoGetRanKBy2Systems();

        request.setAttribute("userlistStr", ulStr);
        request.getRequestDispatcher("/Ranks.jsp").forward(request, response);


    }
}
