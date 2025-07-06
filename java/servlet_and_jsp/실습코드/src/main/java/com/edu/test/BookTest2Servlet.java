package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookOutput")
public class BookTest2Servlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		Book book = (Book) req.getAttribute("book");
		PrintWriter out = resp.getWriter();
		
		out.print("제목 : " + book.getTitle() + " </br>");
		out.print("작가 : " + book.getAuthor() + " </br>");
		out.print("출판사 : " + book.getPublisher() + " </br>");
		
		out.close();
	}
}
