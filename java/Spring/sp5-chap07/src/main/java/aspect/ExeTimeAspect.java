package aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect //공통기능 정의 어노테이션
public class ExeTimeAspect {
	@Pointcut("execution(public * chap07..*(..))") //공통기능을 적용할 대상, 공통기능을 적용할 조건, AspectJ 표현식 공백 주의
	private void publicTarget() {
	}
	
	/*
	 * @Around : Advice의 종류, 언제 공통 기능을 적용할 것인지.
	 * (속성) : 언제의 조건, 포인트컷 표현식 or 포인트컷 메소드 호출
	 * 			ex. @Around("publicTarget()") : publicTarget()의 조건이 성립할때 실행 한다.
	 * 		- 포인트컷 표현식 : AspectJ 포인트컷 문법(execution, within, bean, @annotation, 포인트컷 조합가능(&&, ||, !))
	 * 		- 포인트컷 메소드 호출 : @Pointcut의 메소드 시그니처
	 * 
	 * 그럼 왜 @Pointcut과 Advice(@Around, ..)를 나눌까?
	 * 		조건을 재사용 하려고 나눈다.
	 * 
	 * Advice(@Around, ..) 조건에 매칭되는 빈이 있으면, 그 빈에 대한 프록시를 생성해 컨테이너에 등록한다.
	 * 프록시 생성 시점 : 빈 초기화 이후 BeanPostProcessor 후처리 단계에서 조건에 매칭되는 빈이 있으면 프록시로 교체됨.(그후 컨테이너에 등록)
	 * */
	@Around("publicTarget()") //어떤 조건에서 공통기능을 실행할 것인가.(여기에서 어떤 조건은 Pointcut에 대한 조건)
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {//ProceedingJoinPint를 프록시 대상 객체 메소드 호출할 때 사용됨
		long start = System.nanoTime();
		try {
			Object result = joinPoint.proceed();
			return result;
		} finally {
			long finish = System.nanoTime();
			Signature sig = joinPoint.getSignature();
			System.out.printf("%s.%s(%s) 실행시간 : %d ns\n", joinPoint.getTarget().getClass().getSimpleName(), sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish -start));
			Object target = joinPoint.getTarget();
			Object[] targetArgs = joinPoint.getArgs();
			
			String name = sig.getName();
			String allMethod = sig.toLongString();
			String methodName = sig.toShortString();
			
			System.out.println(
				    "getSignature() : 메소드 시그니처를 구한다." + sig +
				    "\ngetTarget() : 대상 객체를 구한다." + target +
				    "\ngetArgs() : 파라미터 목록을 구한다." + targetArgs +
				    "\n\ngetName() : 호출되는 메소드 이름" + name +
				    "\ntoLongString() : 호출되는 메소드 완전형 문장(반환타입, 메소드 시그니처, 파라미터 타입)" + allMethod +
				    "\ntoShortString() : 호출되는 메소드 축약형 문장" + methodName
				);
		}
	}
}
