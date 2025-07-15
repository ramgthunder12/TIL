<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sql:query var="rs" dataSource="jdbc/myoracle">
	select * from test
</sql:query>
	<table>
		<tr>
			<c:forEach var="columnName" items="${rs.columnNames}">
				<th><c:out value="${columnName}"/></th>
			</c:forEach>
		</tr>
		<c:forEach var="row" items="${rs.rows}">
			<tr>
				<th><c:out value="${row.id}"/></th>
				<th><c:out value="${row.pwd}"/></th>
			</tr>
		</c:forEach>
	</table>
	
<hr>	
<sql:update dataSource="jdbc/myoracle">
	update test set pwd=? where id=?
		<sql:param value="${'123'}"/>
		<sql:param value="${'aa'}"/>
</sql:update>

<sql:query var="result" dataSource="jdbc/myoracle">
	select * from test
</sql:query>
	<table>
		<tr>
			<c:forEach var="columnName1" items="${result.columnNames}">
				<th><c:out value="${columnName1}"/></th>
			</c:forEach>
		</tr>
		<c:forEach var="row1" items="${result.rows}">
			<tr>
				<th><c:out value="${row1.id}"/></th>
				<th><c:out value="${row1.pwd}"/></th>
			</tr>
		</c:forEach>
	</table>
