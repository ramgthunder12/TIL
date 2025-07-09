package com.edu.customTag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyCustomTag2 extends SimpleTagSupport{
	@Override
	public void doTag() throws JspException, IOException {
		System.out.println("커스텀 태그2의 바디가 실행 되기 전");
		for(int i = 0; i < 3; i++) {
			getJspBody().invoke(null);
		}
		System.out.println("커스텀 태그2의 바디가 실행 되기 후");
	}
}
