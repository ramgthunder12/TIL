package com.edu.test;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class TestSessionListener implements HttpSessionListener {
	public TestSessionListener() {
		System.out.println("TestSessionLIstener 객체 생성");
	}
	
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("Session 객체 생성");		
	}
	
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("Session 객체 삭제");		
	}
}
