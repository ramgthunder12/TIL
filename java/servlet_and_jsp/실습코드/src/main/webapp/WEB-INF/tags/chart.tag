<%@ tag body-content="scriptless" pageEncoding="utf-8" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="color" required="true" %>

<table border="1">
	<tr>
		<th>${title}</th>
	</tr>
	<tr>
    	<td bgcolor="${color}">
    	<jsp:doBody />
		</td>
	</tr>
</table>
