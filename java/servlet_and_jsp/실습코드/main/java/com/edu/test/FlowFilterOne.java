package com.edu.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FlowFilterOne implements Filter{
	public void init(FilterConfig config) {		
		System.out.println("init() 호출 ............one");
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		System.out.println("doFilter() 호출전 ............one");
		chain.doFilter(req, resp);
		System.out.println("doFilter() 호출후 ............one");
	}
	
	public void destroy() {
		System.out.println("destory() 호출 ............one");
	}
}
