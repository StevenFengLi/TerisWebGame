package com.wzc.loginServlet;

import com.wzc.Dao.UserDao;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rpsw = request.getParameter("rpsw");
		if(username==null||username.trim().isEmpty()){
			request.setAttribute("msg", "帐号不能为空");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(password==null||password.trim().isEmpty()){
			request.setAttribute("msg", "密码不能为空");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if(!password.equals(rpsw)){
			request.setAttribute("msg", "两次输入的密码不同");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		UserDao u = new UserDao();
		u.addUser(username,password);
		request.setAttribute("msg", "恭喜："+username+"，注册成功");
		request.getRequestDispatcher("/index.jsp").forward(request, response);

	}

}
