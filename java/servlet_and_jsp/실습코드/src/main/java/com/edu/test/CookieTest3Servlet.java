package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cookie3")
public class CookieTest3Servlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		int count = 0;
		
		
		Cookie[] cookies = req.getCookies();
		
		for(int i = 0; cookies != null && i < cookies.length; i++) {
			if(cookies[i].getName().equals("count")) {
				count = Integer.parseInt(cookies[i].getValue());
			}
		}
		Cookie c1 = new Cookie("count", ++count + "");
		resp.addCookie(c1);
		
		out.print("방문횟수 : " + count);
		out.close();
	}
}
