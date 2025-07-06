package com.edu.test;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class TestRequestListener implements ServletRequestListener{
	public TestRequestListener() {
		System.out.println("HttpServletRequest 객체 생성");
	}
	
	public void requestInitialized(ServletRequestEvent event) {
		System.out.println("HttpServletRequest 객체 초기화");
	}
	
	public void requestDestroyed(ServletRequestEvent event) {
		System.out.println("HttpServletRequest 객체 삭제");
	}
}
