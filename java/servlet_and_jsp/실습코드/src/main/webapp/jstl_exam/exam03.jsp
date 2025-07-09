<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String str = request.getParameter("msg"); %>

<c:catch var="e">
	<%
		if(str.equals("add")) {
			out.print(str);
		}
	%>
</c:catch>