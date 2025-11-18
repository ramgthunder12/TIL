# 프록시와 AOP  

## AOP 란
Aspect Orientide Programming
관심사를 분리해 모듈화 하는 방식

기능 관심사
핵심기능 비지니스
공통기능 로깅, 트랜잭션, 보안 등

공통기능과 핵심기능을 분리해  핵심 기능에 공통기능을 삽입하는 프로그래밍 하는 방식

공통기능을 재사용할 수 있고
핵심기능의 수정 없이 공통기능을 추가 할 수 있다.

### 공통기능 삽입 방식
1. 컴파일 시점에서 바이트코드로 공통기능 삽입(Compile-time Weaving)
2. 클래스로딩 시점에서 바이트코드로 공통기능 삽입(Load-time Weaving)
3. 런타임 시점에서 프록시 객체를 생성해 공통 기능 삽입(Runtime Weaving)

- AspectJ는 컴파일 또는 클래스 로딩 시점에서 공통기능 삽입을 수행한다.
- Spring AOP는 런타임 시점에 자동으로 프록시 객체를 생성해  공통기능을 수행한다. AspectJ의 pointcut 표현식과 라이브러리를 사용한다.

> weaving : advice를 핵심 로직 코드에 적용하는것

## 프록시 란
핵심 기능 실행할 때, 핵심 기능을 수정하지 않고 공통기능을 추가하고 제어 하기 위한 객체 이다.
> 핵심 기능은 만들어져 있다.
프록시는 공통 기능을 구현한다.

# 스프링 AOP구현 방법
## 구현 방법
### pointcut 정의
어떤 집합(구역)에서 공통기능을 실행 시킬 것인가 정의
### advice 정의
메인 기능을 실행시킬때 공통기능은 언제 실행 시킬것인가 정의, 단 spring은 around만 가능
### advice 종류
- After : 메인 기능 전에 공통기능 호출
- Before : 메인 기능 후에 공통기능 호출
- Around : 메인 기능 전, 후에 둘다 공통기능 호출
- After Throwing : 메인 기능에 예외 발생 시 공통기능 호출
- After Returning : 대상 객체의 메서드가 익셉션 없이 실행된 이후 공통 기능 호출

### joinpoint
Advice 적용 지점을 의미  
메서드 호출, 필드 값 변경 등
Spring은 프록시를 이용해 AOP를 구현 하기 때문에 메서드 호출에 대한 joinpoint만 지원  
> 프록시(InvocationHandler, MethodInterceptor)는 실제 객체의 메서드 호출을 가로 채서 공통기능을 수행함으로 메서드 호출에 대한 joinpoint만 지원

### [`execution 명시자 표현식`](./execution명시자표현식.txt)

### ProceedingJoinPoint 메서드
Around Advice에서 ProceedingJoinPoint의 proceed()를 호출하면 공통기능을 실행 시킬 수 있다.
- ProceedingJoinPoint 인터페이스가 제공하는 메서드
	- Signature getSignature() : 
	- Object getTarget() : 대상 객체를 반환함
	- Object[] getArgs() : 파라미터 목록을 반환함
- org.aspectj.lang.Signature 인터페이스가 제공하는 메서드
	- String getName() : 호출되는 메소드의 이름을 반환함
	- String toLongString() : 
	- String toShortString() :

# 프록시 생성 방식
### 프록시 생성 방식
빈을 생성할 때 사용한 클래스를 바탕으로 프록시가 생성된다. 하지만 클래스가 인터페이스를 구현하고 있다면 빈을 생성할 때 사용한 클래스의 인터페이스를 바탕으로 프록시가 생성 된다. 따라서 인터페이스를 상속한 클래스로 빈을 만들때에는 
설정 클래스(@Configuration)에 
@EnableAspectJAutoProxy(proxyTargetClass = true)를 지정해 인터페이스가 아닌 클래스를 상속받아 프록시를 생성하도록 만들어야 한다.

### Advice 적용 순서
같은 JoinPoint 사용 시, Advice 실행 순서가 보장되지 않으므로 @Order을 사용하여 우선 순위를 지정 해야한다.
- @Order(n)  
n은 int값을 가짐, 수가 작을 수록 높은 우선 순위를 가짐

### @Around의 Pointcut 설정


### @Pointcut 재사용
별도의 클래스에 @Ponintcut만 분리 해서 재사용 할수 있다.
분리한 Pointcut이 담긴 클래스는 Bean으로 등록할 필요가 없다.
