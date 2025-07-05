package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/third")
public class ThirdServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Third Start!");
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		Enumeration<String> parameterNames = req.getParameterNames();
		
		String name;
		while(parameterNames.hasMoreElements()) {
			name = parameterNames.nextElement();
			System.out.println(name);
			out.print("<h3>request.getParameter(String name) : " + req.getParameter(name) + "</h3>");
		}
		
		out.close();
	}
}
