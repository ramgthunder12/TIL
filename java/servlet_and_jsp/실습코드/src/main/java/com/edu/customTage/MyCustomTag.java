package com.edu.customTage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class MyCustomTag extends TagSupport {
	@Override
	public int doStartTag() throws JspException {
		System.out.println("시작태그를 만났습니다.");
		return super.doStartTag();
	}
	
	@Override
	public int doEndTag() throws JspException {
		System.out.println("끝태그를 만났습니다.");
		return super.doEndTag();
	}
	
}
