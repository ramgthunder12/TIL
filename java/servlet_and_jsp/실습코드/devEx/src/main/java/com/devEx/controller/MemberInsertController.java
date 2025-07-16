package com.devEx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devEx.service.MemberService;
import com.devEx.vo.MemberVO;

public class MemberInsertController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//파라미터 추출
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		//유효성 체크
		if(id.isEmpty() || passwd.isEmpty() || name.isEmpty() || mail.isEmpty()) {
			request.setAttribute("error", "모든 항목을 빠짐없이 입력해주시기 바랍니다!");
			return ;
		}
		//VO 객체에 데이터 바인딩
		MemberVO member = new MemberVO();
		member.setId(id);
		member.setPasswd(passwd);
		member.setName(name);
		member.setMail(mail);
		
		//Service 객체의 메소드 호출
		MemberService service = MemberService.getInstance();
		service.memberInsert(member);
		
		//Output View 페이지로 이동
		request.setAttribute("id", id);
		HttpUtil.forward(request, response, "/result/memberInsertOutput.jsp");
	}
}
