<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>조회</title>
</head>
<body>

<h3>회원 정보 검색</h3>

${error}

<form action="memberSearch.do" method="post">
	ID : <input type="text" name="id"/><br>
	<input type="hidden" name="job" value="search"/>
	<input type="submit" value="검색"/>
</form>
</body>
</html>