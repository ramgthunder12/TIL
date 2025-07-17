package com.devEx.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devEx.service.MemberService;
import com.devEx.vo.MemberVO;

public class MemberListController implements Controller {
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MemberService service = MemberService.getInstance();
		ArrayList<MemberVO> list = service.memberList();
		
		request.setAttribute("list", list);
		HttpUtil.forward(request, response, "/result/memberListOutput.jsp");
	}
}
