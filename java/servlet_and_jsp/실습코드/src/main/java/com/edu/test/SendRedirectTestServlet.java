package com.edu.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/site")
public class SendRedirectTestServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String site = req.getParameter("site");
		
		if(site.equals("naver")) {
			resp.sendRedirect("http://www.naver.com");
		} else if(site.equals("daum")) {
			resp.sendRedirect("http://www.daum.net");
		} else if(site.equals("op.gg")) {
			resp.sendRedirect("http://op.gg/ko");
		} else if(site.equals("google")) {
			resp.sendRedirect("http://www.google.com");
		}
	}
}
