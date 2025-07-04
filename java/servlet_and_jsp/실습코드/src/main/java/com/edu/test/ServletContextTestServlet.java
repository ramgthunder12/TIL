package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servletContext")
public class ServletContextTestServlet extends HttpServlet {
	ServletContext sc;
	
//	@Override
//	public void init(ServletConfig servletConfig) throws ServletException {
//		sc = servletConfig.getServletContext();
//	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		sc = this.getServletContext();
		String location = sc.getInitParameter("contextConfig");
		
		out.print("<html>");
		out.print("<head>");
		out.print("<title>");
		out.print("</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<h3>");
		out.print("servletContext : " + sc);
		out.print("location : " + location);
		out.print("</h3>");
		out.print("</body>");
		out.print("</html>");
		
	}
}
