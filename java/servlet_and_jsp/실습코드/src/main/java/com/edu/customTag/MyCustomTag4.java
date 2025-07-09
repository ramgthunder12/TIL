package com.edu.customTag;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyCustomTag4 extends SimpleTagSupport {
	private int num1;
	private int num2;

	public int getNum1() {
		return num1;
	}


	public void setNum1(int num1) {
		this.num1 = num1;
	}


	public int getNum2() {
		return num2;
	}


	public void setNum2(int num2) {
		this.num2 = num2;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspContext context = this.getJspContext();
		JspWriter out = context.getOut();
		out.print(num1 + " + " + num2 + " = " + (num1 + num2));
	}
}
