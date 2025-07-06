package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/local")
public class LocalTestServlet extends HttpServlet {
	String str;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int number = 0;
		str = req.getParameter("msg");
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>MultiThread</title></head><body>");
		out.println("<body><h2>처리 결과(지역변수)</h2>");
		
		while(number++ < 10) {
			out.print(str + " : " + number + "<br/>");
			out.flush();
			System.out.println(str + " : " + number);
			
			try {
				Thread.sleep(3000);
			} catch(Exception e) {
				System.out.println(e);
			}
			
		}
		
		out.println("<h2>Done " + str + "</h2></body></html>");
		out.close();
	}
}
