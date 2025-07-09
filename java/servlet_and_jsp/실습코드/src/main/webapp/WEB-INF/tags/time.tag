<%@ tag body-content="scriptless" pageEncoding="utf-8" %>
<%@ tag import="java.util.Calendar" %>

<%   Calendar date = Calendar.getInstance();
	JspWriter outPrint = getJspContext().getOut();
	outPrint.print(date.get(Calendar.YEAR));
	outPrint.print(" - ");
	outPrint.print(date.get(Calendar.MONTH) + 1);
	outPrint.print(" - ");
	outPrint.print(date.get(Calendar.DATE));
%>