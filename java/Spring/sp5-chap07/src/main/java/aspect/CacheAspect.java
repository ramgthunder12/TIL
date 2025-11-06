package aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2) // ExeTimeAspect 다음에 실행 (Order없으면 빈등록을 먼저한 순서대로 실행됨, ApplicationContext가 스캔한 순서) 에 따라 체인을 만든다)
public class CacheAspect {
    private Map<Long, Object> cache = new HashMap<>();

    @Pointcut("execution(public * chap07..*(long))")
    public void cacheTarget() {    
    }

    @Around("cacheTarget()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Long num = (Long) joinPoint.getArgs()[0];
        if(cache.containsKey(num)) {
            System.out.printf("CacheAspect : Cache에서 구함[%d]\n", num);
            return cache.get(num);
        }

        Object result = joinPoint.proceed();
        cache.put(num, result);
        System.out.printf("CacheAspect: Cache에 추가[%d]\n", num);
        return result;
    }
}