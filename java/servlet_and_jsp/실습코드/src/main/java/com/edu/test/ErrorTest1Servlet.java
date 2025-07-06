package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/errorTest")
public class ErrorTest1Servlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String getQuery = req.getQueryString();
		//getQuery = null;
		out.print("Query : " + getQuery + "</br>");
		out.print("Query 길이 : " + getQuery.length());
		out.print("Done!");
		
		out.close();
		
	}
}
