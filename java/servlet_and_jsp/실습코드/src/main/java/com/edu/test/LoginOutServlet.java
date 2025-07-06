package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginOutServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		
		if(id.isEmpty() || password.isEmpty()) {
			out.print("ID 또는 Password를 입력해주세요.");
			return ;
		}
		
		HttpSession session = req.getSession();
		
		if(session.isNew() || session.getAttribute("id") == null) {
			session.setAttribute("id", id);
			out.print(id + " 로 ");
			out.print("로그인을 완료하였습니다.");
		} else {
			out.print(id + " 로 ");
			out.print("현재 로그인 상태 입니다.");
		}
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		HttpSession session = req.getSession(false);
		
		if(session != null && session.getAttribute("id") != null) {
			out.print(session.getAttribute("id") + " 님이 로그아웃하였습니다.");
			session.invalidate();
			out.print("로그아웃 작업 완료하였습니다.");
		} else {
			out.print("현재 로그인 상태가 아닙니다.");
		}
		out.close();
	}
	
		
}
