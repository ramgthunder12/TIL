package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/queryTest")
public class QueryTestServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		out.print("<html><head><title>Qurey 문자열 테스트</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<h1>Get 방식으로 요청 되었습니다.</h1>");

		String id = req.getParameter("id");
		String password = req.getParameter("pwd");
		String name = req.getParameter("name");
		String[] hobby = req.getParameterValues("hobby");
		String gender = req.getParameter("gender");
		String religion = req.getParameter("religion");
		String intoro = req.getParameter("introduction");
		
		out.print("ID : " + id + "<br/>");
		out.print("PWD : " + password + "<br/>");
		out.print("name : " + name + "<br/>");
		
		out.print("hobby : ");
		int len = hobby.length;
		for(int i = 0; i < len; i++) {
			out.print(hobby[i]);
		}
		out.print("<br/>");
		out.print("gender : " + gender + "<br/>");
		out.print("religion : " + religion + "<br/>");
		out.print("itoroduction : " + intoro + "<br/>");
		
		out.print("</body>");
		out.print("</html>");
		out.close();
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print("<html><head><title>Qurey 문자열 테스트</title>");
		out.print("</head>");
		out.print("<body>");
		out.print("<h1>Post 방식으로 요청 되었습니다.</h1>");

		ServletInputStream input = req.getInputStream();
		int len = req.getContentLength();
		
		byte[] buf = new byte[len];
		input.readLine(buf, 0, len);
		
		String s = new String(buf);
		
		out.print("<h3>전체 문장 : " + s + "</h3>" );
		
		String id = req.getParameter("id");
		String password = req.getParameter("pwd");
		String name = req.getParameter("name");
		String[] hobby = req.getParameterValues("hobby");
		String gender = req.getParameter("gender");
		String religion = req.getParameter("religion");
		String intoro = req.getParameter("introduction");
		
		out.print("ID : " + id + "<br/>");
		out.print("PWD : " + password + "<br/>");
		out.print("name : " + name + "<br/>");
		

		out.print("</body>");
		out.print("</html>");
		out.close();
	}
	
	
}
