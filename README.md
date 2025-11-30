# TIL  
Today I Learned  
**매일 공부한 내용을 기록하는 레포지토리**  

- Notion에서 작성한 개발및 공부 기록  
- 독서 및 실습 프로젝트



## 🔗 주요 설명

### :file_cabinet: [`NotionTIL/`](./NotionTIL)  
Notion에서 작성된 **최신 TIL 콘텐츠**를 변환하여 담은 디렉토리


### :brain: [`TILDB/`](./TILDB)  
Notion의 TIL 콘텐츠가 수정될 때마다 백업되는 **버전 관리용 디렉토리**


### :test_tube: [`notion-sync/`](./notion-sync)  
로컬 환경에서 Notino API 연동을 테스트하기 위한 **테스트 디렉토리**
- Notion -> Markdown 변환 스크립트:
  - [`notion-sync/notion_to_md.py`](./notion-sync/notion-sync/notion_to_md.py) : Notion Database의 페이지/블록을 가져와 Markdown 파일로 변환하고 저장. 충돌 해결 및 메일 알림 기능 포함.
👉 [Notion to GitHub 사용 방법 보러가기](https://github.com/ramgthunder12/notion-to-github)


### ⚙️ [`.github/workflows`](./.github/workflows)
Github Actions 워크플로우
- [`/notion-to-github.yml`](./.github/workflows/notion-to-github.yml) : 스캐줄러를 통해 Notion DB → Markdown 변환 결과를 저장소에 자동 반영


### :test_tube: [`experiments/`](./experiments)  
실험 및 조사 관련 자료를 모아둔 디렉토리
- [스케줄러 실행 시간 결정 이유](./experiments/commit_hour_stats_13_weeks.txt)


### :technologist: [`java/`](./java) 실습 코드
자바 관련 디렉토리
  - [`Spring`](./java/Spring) : 초보 웹 개발자를 위한 스프링5 프로그래밍 입문 도서를 바탕으로 실습 및 정리  
  - [`servlet_and_jsp`](./java/servlet_and_jsp) : 처음 해보는 Servlet & JSP 웹 프로그래밍 도서를 바탕으로 실습 및 정리
  - [`This_is_Java/`](./java/This_is_Java) : 이것이 자바다 도서를 바탕으로 자바 기초 문법 실습과 디자인패턴 및 개념을 자바로 구현 
  - [`dev_skill_interview_note/`](./java/dev_skill_interview_note) : 개발자 기술 면접 노트를 바탕으로 자바 알고리즘 문제 연습
  - [`algorithms`](./java/algorithms/) : 알고리즘 문제 풀이 및 코딩테스트 알고리즘 연습
  - [`syntax`](./java/syntax) : 추가적으로 학습한 Java 문법 정리


---

## 📌 TIL 사용 기술

- Python
- Notion API
- GitHub Actions
