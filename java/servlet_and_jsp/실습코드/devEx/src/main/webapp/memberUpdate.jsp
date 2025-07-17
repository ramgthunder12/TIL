<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.devEx.vo.MemberVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정</title>
</head>
<body>

<h3>수정 정보 검색</h3>

${error}

<form action="memberSearch.do" method="post">
	ID : <input type="text" name="id"/><br>
	<input type="hidden" name="job" value="update"/>
	<input type="submit" value="검색"/>
</form>

<% MemberVO member = (MemberVO) request.getAttribute("member");
	if(member != null) {
%>		<h3>회원 정보 수정</h3>
		<form action="memberUpdate.do" method="post">
			ID : <input type="text" name="id" readonly value="${member.id}"><br>
			비밀번호 : <input type="text" name="passwd"  value="${member.passwd}"><br>
			이름 : <input type="text" name="name"  value="${member.name}"><br>
			메일 : <input type="text" name="mail"  value="${member.mail}"><br>
			<input type="submit" value="수정"/>
		</form>
<%		
	} else {
%>		${result}
<%
	}
%>
</body>
</html>