# Reason : 

**왜 해?**

- [ ] Object… args 라는 파라미터를 보았다. 그럼 Object… 이라는 타입이 있는건가?
# Action : 

**뭘 했어?**

varargs(가변인자)Java5, JDK1.5에서 추가됨

```java
void print(Object... args) {

}
```
- 가변인자는 가장 마지막 파라미터에 사용해야한다.

> If the last formal parameter is a variable arity parameter, the method is a *variable arity method*. Otherwise, it is a *fixed arity method*.

https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html?utm_source=chatgpt.com
> “Invocations of a variable arity method … All the actual argument expressions that do not correspond to the formal parameters preceding the variable arity parameter will be … stored into an array that will be passed to the method invocation (§15.12.4.2).” https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html?utm_source=chatgpt.com
- 가변 인자로 선언된 파라미터의 **정식 타입은 배열 타입(Array Type)** 이다.
- 메서드 호출 시 /**가변 인자 위치**에 전달된 실제 인자들은 **배열로 만들어져서(new T[]{…})** 그 메서드 호출에 전달된다.
> It is a compile-time error to use mixed array notation for a variable arity parameter.(https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html?utm_source=chatgpt.com)
<details>
<summary>arity</summary>

    count, number와 관련된 property

    학술 용어

    = parameter count

</details>

JLS에서 가변 인자 파라미터 선언 시 가변인자 표기와 혼합 배열 표기법 사용을 허용하지 않는다.

<details>
<summary>JLS</summary>

    Java Language Specification

    자바 언어의 공식 규격서(언어 사양서)

    자바 문법, 타입 규칙, 컴파일 규칙, 바이트코드 의미 등 자바가 어떻게 동작 해야 하는지를 정의한 문서

</details>

<details>
<summary>mixed array notiation 혼합 배열 표기법</summary>

    타입 옆의 배열 표기와 변수명 뒤의 배열 표기를 섞는 것

    권장 하지 않지만 
변수 명 뒤의 배열도 표기 가능

    타입 뒤의 [] + 변수명 뒤의 [] = 최종 배열 차원

    하지만 가변 인자에서는 불가능

</details>

ex) int[]… i  가능

int[]… i[] 불가능

int…i[] 불가능

# Insight : 

**뭘 느꼈어?**

## 잘한점

## 개선점

## 배울점

Java5와 JDK1.5는 같다
내부 기술 버전 JDK 1.5 → 브랜드 Java 5

내부기술 버전 JDK 1.1 → 브랜드 JDK 1.0 (1996)

내부기술 버전 JDK 1.2, 1.3, 1.4 → 브랜드 Java2

Java5부터 브랜드와 기술버전 같게 하는 방식 으로 통일

JDK 1.5 → Java5

JDK 1.6 → Java6

JDK 1.7 → Java7


<details>
<summary>J2SE 1.x</summary>

    Java 2 Platform, Standard Edition

    자바 표준 플랫폼 공식 명칭(Java 2), Java SE(Java 5)

</details>

- Java 9부터 정책 바뀜
    - 6개월마다 새버전 출시
<details>
<summary>LTS</summary>

    Long Term Support

    장기 자원 → 애러 많이 잡아서 안정 적임

</details>

- Java 11부터 LTS 3년 마다 지원 정책
- Java 17 나오고 (21년) LTS 2년 마다 지원으로 정책 바뀜