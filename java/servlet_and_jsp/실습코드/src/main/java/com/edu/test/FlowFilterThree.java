package com.edu.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "timer", urlPatterns = "/annoFilterTest")
public class FlowFilterThree implements Filter {
	public void init(ServletConfig config) {
		
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		chain.doFilter(req, resp);
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;
		System.out.println("수행시간 : " + executeTime + "ms");
	}
	
	public void destroy() {
	}
}
