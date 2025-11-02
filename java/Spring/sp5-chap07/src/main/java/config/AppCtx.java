package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import aspect.ExeTimeAspect;
import chap07.Calculator;
import chap07.RecCalculator;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) //자바 코드, "calculator" 프록시의 실제 타입은 RecCalculator를 상속받았으므로 RecCalculator로 타입 변환 가능
//@Enable ... : "..." 관련 기능을 적용하는데 필요한 다양한 스프링 설정 대신 처리 
public class AppCtx {
	@Bean
	public ExeTimeAspect exeTimeAspect() {
		return new ExeTimeAspect();
	}
	
	@Bean
	public Calculator calculator() {
		return new RecCalculator();
	}
}
