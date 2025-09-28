# Reason : 

**왜 해?**

- [ ] Double보다 큰 값은 java에서 어떻게 다룰까?
# Action : 

**뭘 했어?**

### BigDecimal

double 범위를 넘는 소수, 고정소수점 계산 가능

### BigInteger

long범위를 넘는 정수, 암호학, 고정 크기 이상의 정수 

# Insight : 

**뭘 느꼈어?**

bigDecimal은 금융 이자 계산할때 활용된다는데. 내부 동작이 궁금하다.

    ```sql
public class BigInteger implements Serializable {
    private final int[] mag;   // magnitude: 절댓값을 저장하는 배열
    private final int signum; // 부호: -1 (음수), 0 (0), 1 (양수)
}
```
    덧셈, 곱셈 연산

    - int[] : 32비트 단위로  나눠서 수를 표현한다.
    - 자리수 단위로 더(곱)하면서 carry 값을 다음 자릿수로 넘겨 계산 한다.
    - 결과도 int[]로 반환
## 잘한점

내부적인 방법 알아 본것

## 개선점

- [ ] 직접 구현 해볼껄
- [ ] Serializable 에 대해 알아보자
## 배울점
