<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Output</title>
</head>
<body>
	<%
		if(request.getMethod().equals("POST")) {%>
		<%
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
	
			if(id.isEmpty() || pwd.isEmpty()) {
				request.setAttribute("error", "id 또는 pwd를 입력해 주세요!");
				RequestDispatcher rd = request.getRequestDispatcher("logInOut.jsp");
				rd.forward(request, response);
				return;	
			}
			if(session.isNew() || session.getAttribute("id") == null) {
				session.setAttribute("id", id);
			}
			%>
		<%= id %> / <%= pwd %>
	<%
		} else if(request.getMethod().equals("GET")) {
			if(session != null && session.getAttribute("id") != null) {
				session.invalidate();
				out.print("로그아웃 작업이 완료 되었습니다.");
			} else {
				out.print("현재 로그인 상태가 아닙니다.");
			}
		}
	%>
	<%
		RequestDispatcher rd = request.getRequestDispatcher("logInOut.jsp");
		rd.forward(request, response);
	%>
</body>
</html>