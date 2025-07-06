package com.edu.test;

import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;

public class TestServletContextAttributeListener implements ServletContextAttributeListener{
	public TestServletContextAttributeListener() {
		System.out.println("TestServletContextAttributeListener 객체 생성");
	}
	
	public void attributeAdded(ServletContextEvent evnet) {
		System.out.println("TestServletContextAttributeListener객체의 속성 추가");
	}
	
	public void attributeRemove(ServletContextEvent event) {
		System.out.println("TestServletContextAttributeListener객체의 속성 삭제");
		
	}
	
	public void attributeReplaced(ServletContextEvent event) {
		System.out.println("TestServletContextAttributeListener객체의 속성 대체");
		
	}
}
