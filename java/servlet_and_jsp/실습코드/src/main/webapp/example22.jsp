<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>example</title>
</head>
<body>
	<jsp:useBean class="com.edu.beans.BookBean" id="book"/>
	<jsp:setProperty property="*" name="book"/>
	<%
		application.setAttribute("book", book);
	%>
	<jsp:forward page="bookOutput.jsp"/>
</body>
</html>