# Reason : 

**왜 해?**

책의 구절이 이해가 가지 않았다.

> HttpServletRequest의 상위 객체인 ServletRequest는 클라이언트로부터 서비스 요청이 들어올 때 호출되는 HttpServlet의 service(ServletRequest, ServletResponse) 메소드의 첫 번째 인자로 전달되며, 이 메소드는 다시 service(HttpServletRequest, HttpServletResponse) 메소드를 호출합니다.
처음해보는 Servlet&JSP 웹프로그래밍 103p

# Action : 

**뭘 했어?**

1. **글을 이해를 못했다.**
    첫 번째 인자로 전달 된다는 말을 정확하게 이해 하지 못한 상태에서 계속 읽다 보니 앞에서 service()를 재정의 했다는 부분이 생각이 났다.

    그럼 재정의한 service는 인자로 ServletRequest가 있는 메소드 일까 HttpServletRequest가 있는 메소드일까 궁금했다. 

    책의 앞부분에서 Servlet 인터페이스의 service() 메소드가 ServletRequest를 인자로 가지고 있었다.
하지만 예제에서 HttpServlet 클래스는 오버라이드 된service() 메소드에 HttpServletRequest를 인자로 가지고 있었다.

    그래서 두개의 메소드 중에서 어떤것이 오버라이드 한것일까 궁금했다.

1. **HttpServlet에 service(ServletRequest, ServletResponse)와 service(HttpServletRequest, HttpServletResponse)가 둘다 있다.**
    Servlet클래스의 service(ServletRequest, ServletResponse)를 HttpServlet에서 재정의 하고, 재정의된 service(ServletRequest, ServletResponse)를 service(HttpServletRequest, HttpServletResponse)로 오버로딩 했다. 

        와우, 나는 오버라이드, 오버로딩 개념을 알고 있다.

        심지어 내가 만든 코드에서도 녹여내 봤다.

        하지만 같이 쓰고 있는 것은 처음 봤다.

         실무에서는 이렇게 많이 쓸것 같다.

        하나를 폭죽처럼 배웠다.

1. **왜 그렇게 오버로드 한것일까 궁금했다.**
    service(ServletRequest, ServletResponse)에서 HttpServletRequest, HttpServleResponse로 바꿔준다음 요청방식에 따라 메소드를 호출해도 되는데, 왜 오버로드 했을까? 궁금했다

1. **단일 책임의 원칙으로 분리 했다고 생각한다.**
    service(ServletRequest, ServletResponse)를 재정의 해 사용하는 부분과 요청 방식에 따라 메소드를 호출하는 부분을 나누기 위해. 오버라이딩 된 service에서 HttpServletRequest, HttpServleResponse)를 호출한다. 오버라이드 된 service(HttpServletRequest, HttpServleResponse)에서 요청 방식에 따라 메소드를 호출한다.

# Insight : 

**뭘 느꼈어?**

오버라이드와 오버로드를 같이 쓸 수 있구나, 개념이 정확해야지 응용을 할 수 있구나 깨닮았다.

왜 사람들이 좋은 코드를 보라고 했는지 오늘 여의도 불꽃 축제 처럼 알았다.

## 잘한점

계속해서 끊임 없이 책을 읽지 않고, 다른 것 공부하다가 다시 책을 3번 읽어본 것.

지치지 않고 계속해서 새로운 시각을 주었다.

어제 언니한테 배운 (문장이 이해가 안되면 주어, 서술어만 먼저 보기)스킬을 사용해 많은 문장들을 이해 했다.

## 개선점

책을 읽을 때 이해가 안가면 어떤 단어에서 이해가 안가는지 체크하고 해당 단어의 뜻을 찾아야겠다.

## 배울점

- [ ] 오버라이드와 오버로드 같이 사용하는 실습 해봐야겠다.
- [ ] 단일 책임의 원칙을 지키는 코드를 작성해야겠다.