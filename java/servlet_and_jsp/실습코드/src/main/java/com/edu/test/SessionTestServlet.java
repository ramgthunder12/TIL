package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/session")
public class SessionTestServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		String param = req.getParameter("p");
		String msg = null;
		HttpSession session = null;
		
		if(param.equals("create")) {
			session = req.getSession();
			if(session.isNew()) {
				msg = "새로운 세션 객체가 생성됨";
			} else {
				msg = "기존의 세션 객체가 리턴됨";
			}
		} else if(param.equals("delete")) {
			session = req.getSession(false);
			if(session != null) {
				session.invalidate();
				msg = "세션 객체 삭제 작업 완료";
			} else {
				msg = "삭제할 세션 객체 존재하지 않음";
			}
		} else if(param.equals("add")) {
			session = req.getSession(true);
			session.setAttribute("msg", "메시지 입니다.");
			msg = "세션 객체에 데이터 등록 완료";
		} else if(param.equals("get")) {
			session = req.getSession(false);
			if(session != null) {
				String str = (String) session.getAttribute("msg");
				msg = str;
			} else {
				msg = "데이터를 추출할 세션 객체가 존재 하지 않음";
			}
		} else if(param.equals("remove")) {
			session = req.getSession(false);
			if(session != null) {
				session.removeAttribute("msg");
				msg = "세션 객체의 데이터 삭제 완료";
			} else {
				msg = "삭제할 세션 객체의 데이터가 존재 하지 않음";
			}
		} else if(param.equals("replace")) {
			session = req.getSession();
			session.setAttribute("msg", "새로운 메시지 입니다.");
			msg = "세션 객제의 데이터 덮어씌움";
		}
		out.print("처리 결과 : " + msg);
		out.close();
	}
}
