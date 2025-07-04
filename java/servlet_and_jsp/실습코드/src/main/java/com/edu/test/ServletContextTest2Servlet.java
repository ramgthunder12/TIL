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

@WebServlet("/servletContext2")
public class ServletContextTest2Servlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		ServletContext sc = this.getServletContext();
		String location = sc.getInitParameter("contextConfig");
		
		out.print("<html>");
		out.print("<head>");
		out.print("<title>");
		out.print("</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<h3>");
		out.print("servletContext : " + sc + "<br/>");
		out.print("location : " + location + "<br/>");
		out.print("서블릿 버전 : " + sc.getMajorVersion() + ".." + sc.getMinorVersion() + "<br/>");
		out.print("서버정보 : " + sc.getServerInfo() + "<br/>");
		out.print("웹애플리케이션 경로 : " + sc.getContextPath() + "<br/>");
		out.print("웹 애플리케이션 이름 : " + sc.getServletContextName() + "<br/>");
		out.print("파일 실제 경로 : " + sc.getRealPath("/name.html") + "<br/>");
		sc.log("로그기록!!!!");
		out.print("</h3>");
		out.print("</body>");
		out.print("</html>");
		
	}
}
