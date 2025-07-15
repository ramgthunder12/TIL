<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setLocale value="en" />
<fmt:setBundle basename="com.edu.bundle.msg" />
<fmt:message var="name" key="name">
	<fmt:param>Amy</fmt:param>
</fmt:message>

<fmt:message var="infomation" key="info">
	<fmt:param>CheaDam</fmt:param>
	<fmt:param>10000</fmt:param>
</fmt:message>

${name}

${infomation}