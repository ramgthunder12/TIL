# Tomcat JDBC DataSource 주요 프로퍼티

- **setInitialSize(int)**  
  커넥션 풀을 초기화할 때 생성할 초기 커넥션 개수를 지정한다.  
  기본값: **10**

- **setMaxActive(int)**  
  커넥션 풀에서 가져올 수 있는 최대 커넥션 개수를 지정한다.  
  기본값: **100**

- **setMaxIdle(int)**  
  커넥션 풀에 유지할 수 있는 최대 커넥션 개수를 지정한다.  
  기본값: **maxActive와 같음**

- **setMinIdle(int)**  
  커넥션 풀에 유지할 최소 커넥션 개수를 지정한다.  
  기본값: **initialSize에서 가져온다.**

- **setMaxWait(int)**  
  커넥션풀에서 커넥션을 가져올 때 대기할 최대 시간을 밀리초 단위로 지정한다.  
  기본값: **30000 (30초)**

- **setMaxAge(int)**  
  최소 커넥션 연결 후 커넥션의 최대 유효 시간을 밀리초 단위로 지정한다.  
  기본값: **0 (0은 유효 시간 없음을 의미함)**

- **setValidationQuery(String)**  
  커넥션이 유효한지 검사할 때 사용할 쿼리를 지정한다. 언제 검사할지는 별도 설정으로 지정한다.  
  기본값: **null (null이면 검사를 하지 않음)**  
  `"select 1"`, `"select 1 from dual"과 같은 쿼리를 주로 사용한다.`

- **setValidationQueryTimeout(int)**  
  검사 쿼리의 최대 실행 시간을 초 단위로 지정한다.  
  이 시간을 초과하면 검사에 실패한 것으로 간주한다.  
  기본값: **-1 (0 이하로 지정하면 비활성화)**

- **setTestOnBorrow(boolean)**  
  풀에서 커넥션을 가져올 때 검사 여부를 지정한다.  
  기본값: **false**

- **setTestOnReturn(boolean)**  
  풀에 커넥션을 반환할 때 검사 여부를 지정한다.  
  기본값: **false**

- **setTestWhileIdle(boolean)**  
  커넥션이 풀에 유휴 상태로 있는 동안에 검사할지 여부를 지정한다.  
  기본값: **false**

- **setMinEvictableIdleTimeMillis(int)**  
  커넥션이 풀에 유휴 상태로 유지할 최소 시간을 밀리초 단위로 지정한다.  
  `testWhileIdle`이 true이면 유휴 시간이 이 값을 초과한 커넥션을 풀에서 제거한다.  
  기본값: **60000 (60초)**

- **setTimeBetweenEvictionRunsMillis(int)**  
  커넥션 풀의 유휴 커넥션을 검사할 주기를 밀리초 단위로 지정한다.  
  기본값: **5000 (5초)**  
  (1초 이하로 설정하면 안 된다.)