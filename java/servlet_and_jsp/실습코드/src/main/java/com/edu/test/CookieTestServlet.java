package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cookie")
public class CookieTestServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		Cookie c1 = new Cookie("id", "guest");
		c1.setPath("/");
		resp.addCookie(c1);
		
		Cookie c2 = new Cookie("code", "0001");
		c2.setPath("/");
		c2.setMaxAge(60 * 60 * 3);
		resp.addCookie(c2);
		
		Cookie c3 = new Cookie("subject", "java");
		c3.setPath("/");
		c3.setMaxAge(60 * 60 * 24 * 3);
		resp.addCookie(c3);
		
		out.print("쿠키 전송 완료");
		out.close();
	}
}
