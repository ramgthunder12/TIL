<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>application</title>
</head>
<body>
	서버명 : <%= application.getServerInfo() %><br>
	서블릿 버전 : <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %>
	<h3>/edu 리스트</h3>
	<% 
		java.util.Set<String> list = application.getResourcePaths("/");
		if(list != null) {
			Object[] obj = list.toArray();
			for(int i = 0; i < obj.length; i++) {
				out.print(obj[i] + "<br>");
			}
		}
	%>
</body>
</html>