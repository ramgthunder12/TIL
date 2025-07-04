package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicToolBarSeparatorUI;

@WebServlet("/initParam")
public class InitParamServlet extends HttpServlet {
	String id;
	String password;
	
//	@Override
//	public void init(ServletConfig servletConfig) throws ServletException {
//		id = servletConfig.getInitParameter("id");
//		password = servletConfig.getInitParameter("password");
//	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.print("<h2>서블릿 초기 추출 변수</h2>");
		out.print("<h3>ID : " + id + "</h3>");
		out.print("<h3>Password : " + password + "</h3>");
		out.close();
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String env = this.getInitParameter("env");
		
		req.setCharacterEncoding(env);
		
		out.print("<h2>서블릿 초기 추출 변수</h2>");
		out.print("<h3>이름 : " + req.getParameter("name") + "</h3>");
//		out.print("<h3>ID : " + req.getParameter("id") + "</h3>");
//		out.print("<h3>Password : " + req.getParameter("password") + "</h3>");
		out.close();
	}
}
