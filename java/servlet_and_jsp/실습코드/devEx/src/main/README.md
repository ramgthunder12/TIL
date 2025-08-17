# 📦 devEx : servlet CRUD 웹 애플리케이션 프로젝트 실습 (MVC2 + 3계층)

MVC2 디자인 패턴과 3계층 아키텍처(Service/DAO)를 바탕으로, Servlet과 JSP로 클라이언트–서버 간 데이터 흐름을 익히는 프로젝트입니다.  

index.jsp 에서 회원 정보 생성 -> 회원 정보 검색 -> 회원 정보 수정 ->회원 정보 삭제 -> 모든 회원 정보 보기 까지 단계별로 실습했습니다.

## 🧰 기술 스택
- JDK 17
- Apache Tomcat 9
- Maven
- Oracle

## 🔗 라우팅 / 엔드포인트
.do 접미사를 유지한 Front Controller 패턴 기반 라우팅
<!--
| 기능    | Method     | Path               | 요청 파라미터(예시)               | 응답             |
| ----- | ---------- | ------------------ | ------------------------- | -------------- |
| 회원 생성 | POST       | `/memberInsert.do` | `id, passwd, name, mail`  | 성공 시 목록/상세로 이동 |
| 회원 조회 | POST | `/memberSearch.do` | `id`                      | 회원 정보 JSP      |
| 회원 수정 | POST       | `/memberUpdate.do` | `id, name, mail, passwd?` | 성공/실패 메시지      |
| 회원 삭제 | POST       | `/memberDelete.do` | `id`                      | 성공 시 목록 이동     |
| 전체 목록 | GET        | `/memberList.do`   | -                         | 회원 목록 JSP      |
-->
