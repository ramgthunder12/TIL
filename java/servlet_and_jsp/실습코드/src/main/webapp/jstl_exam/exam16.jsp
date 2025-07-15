<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*, java.util.*, java.sql.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sql:setDataSource var="myoracle2"
	driver="oracle.jdbc.driver.OracleDriver"
	url="jdbc:oracle:thin:@localhost:1521/XEPDB1"
	user="hr"
	password="hr"/>
	
<sql:query dataSource="${myoracle2}" var="result">
	select * from departments
</sql:query>

<table border="1">
	<tr>
		<th>DEPTNO</th><th>DNAME</th><th>Location</th>
	</tr>
	<c:forEach var="row" items="${result.rows}">
		<tr>
			<th><c:out value="${row.department_id}"/></th>
			<th><c:out value="${row.department_name}"/></th>
			<th><c:out value="${row.location_id}"/></th>
		</tr>
	</c:forEach>
</table>

<sql:transaction dataSource="${myoracle2}">
	<sql:update>
		update departments set location_id=3200 where department_id=10
	</sql:update>
	<sql:update>
		update departments set location_id=3200 where department_id=20
	</sql:update>
	<sql:update>
		insert into departments values(3, 'createContens', 200, 3200)
	</sql:update>
</sql:transaction>

<sql:query dataSource="${myoracle2}" var="result">
	select * from departments
</sql:query>
<table>
	<tr>
		<c:forEach var="columnName" items="${result.columnNames}">
			<th><c:out value="${columnName}"/></th>
		</c:forEach>
	</tr>
	<c:forEach var="row" items="${result.rows}">
		<tr>
			<th><c:out value="${row.department_id}"/></th>
			<th><c:out value="${row.department_name}"/></th>
			<th><c:out value="${row.manager_id}"/></th>
			<th><c:out value="${row.location_id}"/></th>
		</tr>
	</c:forEach>
</table>