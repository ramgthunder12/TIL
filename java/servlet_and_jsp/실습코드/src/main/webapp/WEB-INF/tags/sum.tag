<%@ tag body-content="empty" pageEncoding="utf-8" %>
<!-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
-->
<%@ attribute name="num1" required="true" %>
<%@ attribute name="num2" required="true" %>
<%@ variable name-given="result" variable-class="java.lang.Long" scope="AT_END" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="result" value="${num1 + num2}" />
</body>
</html>