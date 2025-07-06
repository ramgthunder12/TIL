package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookInput")
public class BookTest1Servlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		Book book = new Book();
		book.setTitle(req.getParameter("title"));
		book.setAuthor(req.getParameter("author"));
		book.setPublisher(req.getParameter("publisher"));
		
		req.setAttribute("book", book);
		
		RequestDispatcher reqeustDispatcher = req.getRequestDispatcher("/bookOutput");
		reqeustDispatcher.forward(req, resp);
		
		
	}
}
