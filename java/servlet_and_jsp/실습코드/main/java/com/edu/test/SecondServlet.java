package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/second")
public class SecondServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SecondServlet!!");
		
		PrintWriter out = resp.getWriter();
		
		out.print("<html><head><title>SecondServletTest</title></head>");
		out.print("<body><h1>have a nice day~!</h1></body>");
		out.print("</html>");
		out.close();
	}
}
