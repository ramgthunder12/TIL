package com.edu.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TestServletContextLinstener implements ServletContextListener{
	public TestServletContextLinstener() {
		System.out.println("TestServletContextLinstener 객체 생성");
	}
	
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("TestServletContextLinstener 객체 초기화");
		
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("TestServletContextLinstener 객체 삭제");
		
	}
}
