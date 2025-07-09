package com.edu.customTag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyCustomTag3 extends SimpleTagSupport{
	@Override
	public void doTag() throws JspException, IOException {
		JspContext context = this.getJspContext();
		JspWriter out = context.getOut();
		
		JspFragment body = this.getJspBody();
		StringWriter sw = new StringWriter();
		body.invoke(sw);
		String str = sw.toString();
		out.print(str.toUpperCase());
		
		return;
	}
}
