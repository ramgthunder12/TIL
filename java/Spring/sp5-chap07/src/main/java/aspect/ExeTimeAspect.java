package aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect //공통기능
public class ExeTimeAspect {
	@Pointcut("execution(public * chap07 ..*(..))") //공통기능을 적용할 대상
	private void publicTarget() {
	}
	
	@Around("publicTarget()") //around  advice, 공통기능 적용, publicTarget() : chap07에 위치한 빈 객체의 public 메소드에 @Around가 붙은 measure()에 적용
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {//ProceedingJoinPint를 프록시 대상 객체 메소드 호출할 때 사용됨
		long start = System.nanoTime();
		try {
			Object result = joinPoint.proceed();
			return result;
		} finally {
			long finish = System.nanoTime();
			Signature sig = joinPoint.getSignature();
			System.out.printf("%s.%s(%s) 실행시간 : %d ns\n", joinPoint.getTarget().getClass().getSimpleName(), sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish -start));
		}
	}
}
