<%@ page import="com.edu.beans.BookBean" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% BookBean book = new BookBean(); %>
<c:set target="<%= book%>" property="title" value="The Secret" />
<%= book.getTitle() %> <br>

<c:set var="b" value="<%= book %>" />
<c:set target="${b}" property="author" value="Byrne, Rhonda" />

${b.author}