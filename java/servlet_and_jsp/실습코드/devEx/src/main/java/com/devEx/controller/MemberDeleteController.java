package com.devEx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devEx.service.MemberService;

public class MemberDeleteController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//파라미터 추출
		String id = request.getParameter("id");
		
		//Service 객체의 메소드 호출
		MemberService service = MemberService.getInstance();
		service.memberDelete(id);
		
		//Output View 페이지로 이동
		HttpUtil.forward(request, response, "/result/memberDeleteOutput.jsp");
	}
}
