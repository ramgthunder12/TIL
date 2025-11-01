package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import chap07.Calculator;
import config.AppCtx;

public class MainAspect {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		
		Calculator cal = ctx.getBean("calculator", Calculator.class);
//		RecCalculator cal = ctx.getBean("calculator", RecCalculator.class); //타입불일치 : 프록시가 RecCalculator의 직접적인 구현체 및 자식이 아니기 때문에 예외(BeanNotOfRequiredTypeException) 발생
		long fiveFact = cal.factorial(5);
		System.out.println("cal.factorial(5) = " + fiveFact);
		System.out.println(cal.getClass().getName());
		ctx.close();
	}
}
