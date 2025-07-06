<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구구단</title>
</head>
<body>
	<h3>스크립트릿으로 구구단 출력</h3>
	<%
		for(int i = 2; i < 10; i++) {
			for(int j = 2; i < 10; i++) {%>
				<%= i %> * <%= j %> = <%= i*j %> <br>
			<%}%>			
		<%}%>
</body>
</html>