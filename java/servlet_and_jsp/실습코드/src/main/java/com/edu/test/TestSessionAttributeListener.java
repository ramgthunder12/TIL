package com.edu.test;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class TestSessionAttributeListener implements HttpSessionAttributeListener {
	public TestSessionAttributeListener() {
		System.out.println("SessionAttributeListener 객체 생성");
	}
	
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("Session객체에 속성 추가");
	}
	
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("Session객체에 속성 삭제");
	}
	
	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println("Session객체에 추가된 속성 대체");
	}
}
