# Reason : 

**왜 해?**

- [ ] 더 많은 정보를 정리 하기 위해 코드 분석하기
# Action : 

**뭘 했어?**

```python
import os
import requests
import json
from dotenv import load_dotenv

# ---------------------------
# 환경 변수 불러오기
# ---------------------------
load_dotenv()
NOTION_TOKEN = os.getenv("NOTION_TOKEN")
DATABASE_ID = os.getenv("DATABASE_ID")

headers = {
    "Authorization": f"Bearer {NOTION_TOKEN}",
    "Content-Type": "application/json",
    "Notion-Version": "2022-06-28"
}

# ---------------------------
# Notion API 함수
# ---------------------------
def get_database_pages(database_id):
    """데이터베이스 안의 페이지 목록 가져오기"""
    url = f"https://api.notion.com/v1/databases/{database_id}/query"
    res = requests.post(url, headers=headers)
    res.raise_for_status()
    return res.json()

def get_block_children(block_id):
    """블록(children) 가져오기"""
    url = f"https://api.notion.com/v1/blocks/{block_id}/children?page_size=100"
    res = requests.get(url, headers=headers)
    res.raise_for_status()
    return res.json()

# ---------------------------
# Markdown 변환 함수
# ---------------------------
def rich_text_to_markdown(rich_text_array):
    """Notion rich_text 배열 → Markdown"""
    parts = []
    for rt in rich_text_array:
        text = rt.get("plain_text", "")
        ann = rt.get("annotations", {})
        if ann.get("bold"):
            text = f"**{text}**"
        if ann.get("italic"):
            text = f"*{text}*"
        if ann.get("code"):
            text = f"`{text}`"
        if ann.get("strikethrough"):
            text = f"~~{text}~~"
        if ann.get("underline"):
            text = f"<u>{text}</u>"
        parts.append(text)
    return "".join(parts)

def blocks_to_markdown(blocks_json, indent=0):
    """Notion blocks JSON → Markdown"""
    md_lines = []
    indent_str = "    " * indent  # bullet 들여쓰기

    for block in blocks_json.get("results", []):
        btype = block["type"]
        data = block[btype]

        # Heading
        if btype == "heading_1":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}# {text}\n")

        elif btype == "heading_2":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}## {text}\n")

        elif btype == "heading_3":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}### {text}\n")

        # Paragraph
        elif btype == "paragraph":
            text = rich_text_to_markdown(data.get("rich_text", []))
            if text.strip():
                md_lines.append(f"{indent_str}{text}\n")

        # Bullet list
        elif btype == "bulleted_list_item":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}- {text}")
            if block.get("has_children"):
                children = get_block_children(block["id"])
                md_lines.append(blocks_to_markdown(children, indent + 1))

        # Numbered list
        elif btype == "numbered_list_item":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}1. {text}")
            if block.get("has_children"):
                children = get_block_children(block["id"])
                md_lines.append(blocks_to_markdown(children, indent + 1))

        # To-do
        elif btype == "to_do":
            text = rich_text_to_markdown(data.get("rich_text", []))
            checked = "x" if data.get("checked") else " "
            md_lines.append(f"{indent_str}- [{checked}] {text}")

        # Quote
        elif btype == "quote":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}> {text}")

        # Code block
        elif btype == "code":
            lang = data.get("language", "")
            text = "".join([rt.get("plain_text", "") for rt in data.get("rich_text", [])])
            md_lines.append(f"{indent_str}```{lang}\n{text}\n```")

    return "\n".join(md_lines)

# ---------------------------
# 실행부
# ---------------------------
if __name__ == "__main__":
    # 데이터베이스에서 첫 페이지 ID 가져오기
    db_data = get_database_pages(DATABASE_ID)
    first_page_id = db_data["results"][1]["id"]
    print("First page ID:", first_page_id)

    # 해당 페이지 블록 가져오기
    blocks = get_block_children(first_page_id)

    # Markdown 변환
    markdown_text = blocks_to_markdown(blocks)

    # 저장
    with open("notion_page.md", "w", encoding="utf-8") as f:
        f.write(markdown_text)

    print("✅ 변환 완료! notion_page.md 파일 확인하세요.")
```
### ✅ `get_database_pages(database_id)`

- 역할: Notion DB 안의 **페이지 리스트**를 POST 방식으로 가져옴
- 특징:
    - Notion DB는 `query` 방식(POST + body JSON 사용하는 방식)으로 페이지 목록을 가져와야 함
        <details>
        <summary>query(동사) 방식이란(POST + body JSON 사용하는 방식)</summary>

            데이터베이스 안의 페이지 목록을 조회하는 기능

            POST + body JSON 사용하는 방식

            Body안에 filter, sorts, page_size, start_cursor 같은 조건을 넣어서 검색 가능

            RESTful 스타일을 따르지만 완벽한 REST 규칙은 아님

            - [ ] 왜 사용 할까?
                RESTful 방식을 사용하면 조건에 의해 URL이 길어짐, 따라서 보기 편하게 body에 조건을 넣는 방식을 사용

        </details>

    - 인증은 Bearer Token 사용
        <details>
        <summary>Bearer Token</summary>

            API 요청 시 인증을 위해 HTTP 헤더에 넣는 토큰

            OAuth 2.0 인증 체계에서 쓰이는 방식 중 하나

            - [ ] 왜 사용할까?
                URL, Body에 작성된 내용은 로그에 자동 기록 될 수 있는데, 헤더에 넣으면 노출 위험도가 적음 

        </details>

```python
url = f"https://api.notion.com/v1/databases/{database_id}/query"
```
### ✅ `get_block_children(block_id)`

- 역할: 해당 페이지의 **블록들(children)** 을 가져옴
- 특징:
    - `page_size=100`: 한 번에 최대 100개 블록까지
    - 재귀적으로 자식 블록까지 가져올 수 있도록 설계되어 있음
### ✅ `blocks_to_markdown(blocks_json, indent=0)`

- 역할: 페이지의 블록 JSON을 받아서, 마크다운 포맷으로 변환
- 처리되는 블록 종류:

- 자식 블록이 있으면 `get_block_children()` 재호출 → **재귀 처리**
<details>
<summary>처리되는 블록 종류 추가 정보</summary>

    <details>
    <summary>토글</summary>

        HTML을 사용하여 처리

        toggle_test.md(토글 테스트 완료)

        ```python
# 🧪 토글 테스트 문서

## 기본 텍스트

이건 기본 마크다운 문서입니다.

---

## 🔽 토글 예시 1: 간단한 텍스트

<details>
<summary>여기를 클릭하면 펼쳐집니다</summary>

안녕하세요! 👋  
이 내용은 접혀 있다가 클릭 시 나타납니다.

</details>

---

## 🔽 토글 예시 2: 리스트도 가능

<details>
<summary>할 일 목록 보기</summary>

- [x] API 연결
- [x] 마크다운 변환
- [ ] 전체 페이지 처리
- [ ] README 작성

</details>

---

## 🔽 토글 예시 3: 코드 블록도 가능

<details>
<summary>코드 블록 보기</summary>

```python
def hello():
    print("Hello from toggle!")
```
</details>

```
    </details>

    <details>
    <summary>이미지</summary>

        문제점은 보통 비공개 링크이기 때문에 github에서 보이지 않음

        ```python
![대체텍스트](이미지주소)
[![썸네일](https://img.youtube.com/vi/abc12345678/0.jpg)](https://www.youtube.com/watch?v=abc12345678)

```
    </details>

    <details>
    <summary>테이블</summary>

    </details>

    <details>
    <summary>방정식</summary>

        수식을 이미지로 만들어서 첨부

        ```python
![수식](https://latex.codecogs.com/svg.image?E=mc^2)
```
    </details>

</details>

### ✅ `rich_text_to_markdown(rich_text_array)`

- 역할: Notion의 **rich_text 스타일 요소**를 마크다운 태그로 변환
- 대응되는 스타일:

- rich_text : 사용자가 직접 설정할 수 있는 서식이 있는 텍스트, 콘텐츠의 스타일과 서식을 다양하게 사용자 지정할 수 있는 문서 유형
<details>
<summary>색상 스타일 예시</summary>

    ```python
<details>
<summary>
  <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Hand%20gestures/Eyes.png" alt="Eyes" width="2%" /> 내가 지금 배우는 ... 
</summary>
   <br>
  
![js](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white) ![html](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white) ![css](https://img.shields.io/badge/CSS-239120?&style=for-the-badge&logo=css3&logoColor=white) ![react](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)  
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white) ![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![c](https://img.shields.io/badge/C-00599C?style=for-the-badge&logo=c&logoColor=white) ![python](https://img.shields.io/badge/Python-14354C?style=for-the-badge&logo=python&logoColor=white) ![kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white) ![spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) 

</details>
출처: https://hulrud.tistory.com/3 [주독야독:티스토리]
```
    표현은 가능

    단점 : 검색, 복사 불가, 다국어 처리불편, 폰트 지정 불편

</details>

# Insight : 

**뭘 느꼈어?**

## 잘한점

## 개선점

- [ ] 모든 TIL 페이지 변경하기
    <details>
    <summary>작성목록</summary>

        1. 프로젝트 소개
            1. 설명
                1. 이 프로젝트는 Notion Database 페이지를 Github 저장소에 Markdown 형식으로 자동 백업하는 도구 입니다.
                1. 만들게 된 계기 : 이 프로젝트는 Notion에 작성한 글을 GitHub에도 다시 작성해야 하는 이중 작업 때문에 발생하는 시간과 비용 낭비 문제를 해결하기 위해 만들어졌습니다.
                또한 GitHub 사용이 익숙하지 않은 사람도 **한 번의 작성으로 손쉽게 commit이 가능**하도록 접근성을 높이고자 했습니다.

            1. 기능
                1. Notion DataBase 페이지 명 가져오기(파일명으로 사용할수 있도록 특수기호 제외 \ / * ? : " < > |)
                1. Notion DataBase 모든 페이지 markdown 형식으로 백업 하기
                1. 내용 변경 시 새로운 내용으로 백업해 충돌 해결(git 충돌 마커 자동 제거)
                1. 애러 발생 시 이메일 알림 전송
                1. 텍스트
                    1. 기본글자 plain_text
                    1. 볼드체 bold
                    1. 이테릭체 italic
                    1. 코드 code
                    1. 스트라이크스로우 strikethrough
                    1. 언더라인 underline
                1. 블록
                    1. 헤딩 1, 2, 3
                    1. 파라그래프
                        1. 불릿리스트
                        1. 넘버리스트
                        1. 체크박스
                        1. 쿼트
                        1. 코드
                        1. 토글
                1. 자동 실행
                    1. 한국 시간 기준 오전 8시 실행
                    1. 시간 변경 방법
                        1. 한국 원하는 시간 - 9시간  = 시간 값 작성
                        1. workflow에서 schdule에 23시→ 원한는 시간 넣기
                1. 수동 실행
                    1. github Actions에서 수동 실행 가능
                    1. 수동 실행 방법
            1. 완성된 예시 사진
        1. 사용방법 큰 흐름 설명
            1. github 코드 가져오기 → Notion 설정 → github 설정
        1. 자세한 사용법
            1. 설정법
                1. github 코드 가져오기
                    1. 클론 방법
                1. Notion 설정
                    1. 데이터베이스 생성
                    1. 페이지 생성
                    1. NotionAPI 생성
                    1. Github 연결
                1. github 설정
                    1. github Actions 설정
                    1. github secrit variables 설정
                        <details>
                        <summary>설정 방법</summary>

                            1. settings → values → 토큰 값 생성 → 값 넣기 
                            1. Notion to Github 코드를 클론한 레포지토리의 탭에서 settings 찾기
                                <details>
                                <summary>설명</summary>

                                    클론 : 코드(프로젝트 전체) 복사해서 내 저장소에 붙여 넣기

                                    레포지토리 : 저장소, github home에서 찾을 수 있다. Repositories

                                    탭 : 상단 카테고리를 탭이라고 지칭 하려고 한다

                                </details>

                            1. 왼쪽 카테고리중 Security항목에 Secrets and variables펼치기 Actions 누르기
                                <details>
                                <summary>사진</summary>


                                </details>

                            1. `New repository secret`(초록버튼) 클릭
                            1. Name과 Secret 작성
                                <details>
                                <summary>설명</summary>

                                    1. Name은 필수 작성 항목과 일치하게 작성해야 함으로 복사 붙여넣기를 추천, Name은 보여지는 이름
                                        1. Secret은 보여지지 않는 Name에 따른 값으로 변경 시에도 값을 확인할 수 없음
                                        1. (오타가 생기면 인식을 못함)
                                </details>

                            1. `Add secret`(초록버튼) 클릭
                        </details>

                        <details>
                        <summary>설정 필수 값</summary>

                            1. NOTION_API_KEY : Notion에서 발급 받은 키
                                <details>
                                <summary>발급법</summary>

                                </details>

                                <details>
                                <summary>예시</summary>

                                </details>

                            1. DATABASE_ID : Notion에서 만든 데이터베이스(백업하고 싶은 페이지가 담겨 있는 곳)
                                <details>
                                <summary>발급법</summary>

                                    Notion데이터 베이스의 고유 번호

                                    해당 데이터 베이스를 웹 페이지로 연 다음 000까지의 번호

                                </details>

                                <details>
                                <summary>예시</summary>

                                </details>

                            1. SENDER_EMAIL : 메일 송신 주소
                                <details>
                                <summary>예시</summary>

                                    ramgthunder12@gmail.com

                                </details>

                            1. EMAIL_PASSWORD : 메일 송신자 비밀번호
                                <details>
                                <summary>예시</summary>

                                    NotionToGithub9080$

                                </details>

                            1. RECIVER_EMAIL : 메일 수신 주소(애러 발생 시 알림 받을 주소)
                                <details>
                                <summary>예시</summary>

                                    ramgthunder12@gmail.com

                                </details>

                            1. GH_TOKEN : github에서 발급해주는 토큰(커밋 시 사용)
                                <details>
                                <summary>발급법</summary>

                                    Settings → Developer Settings → Personal access tokens의 Tokens(classic) → Generate new token(classic)

                                    1. 상단 오른쪽 프로필 클릭
                                    1. Settings클릭
                                    1. 왼쪽 사이드바 하단에 Developer settings 클릭
                                    1. 왼쪽 사이드바Personal Access Tokens펼치기
                                    1. Tokens(classic)클릭
                                    1. Generate new token 클릭
                                    1. Gemerate new token (classic)클릭
                                    1.  Note 작성(이름) Notion to Github Sync Token
                                    1. 체크박스 선택
                                        <details>
                                        <summary>체크 박스 선택 항목</summary>

                                            - [ ] workflow : Github Actions 워크플로우 수정 가능
                                            - [ ] write:packages : GitHub Pakage
                                            - [ ] admin:repo_hook
                                            - [ ] delete_repo
                                        </details>

                                </details>

                                <details>
                                <summary>예시</summary>

                                </details>

                        </details>

                    1. github token 설정
                    1. .env
                        <details>
                        <summary>설정 방법</summary>

                            .env파일에서 TILDB글씨를 지우고 파일명을 작성

                        </details>

                        <details>
                        <summary>필수 설정 값</summary>

                            1. OUTPUT_DIR : Notion 페이지를 담을 폴더 명 설정, Notion에 작성한 데이터베이스 명을 넣는 것을 추천 
                        </details>

                    1. 추가 설정
                        1. 시간 설정
                            <details>
                            <summary>설정 방법</summary>

                                .github/workflows폴더에서 workflow_dispatch.yml열기

                                7번 줄 23숫자를 원하는 시간으로 바꾸기

                                UTC 기준 23시가 한국 기준 오전 8시

                                따라서 원하는 한국 시간 -9시간을 한 값을 넣기

                            </details>

                1. 테스트 방법
                    1. 수동 실행 방법
        1. 지원환경
            1. Notion 버전
            1. Github 버전
            1. Github Actions에서 사용하고 있는 리눅스 버전
        1. 문제 해결
            1. 구글 메일 접근 허용
            1. 네이버 매일을 사용하려면 따로 네이버 메일에서 설정 해줘야 함, Gmail 추천
            1. Github Actions 시간대가 UTC 기준이여서 한국 시간을 알고 싶다면, UTC 시간 +9를 하면 한국 시간
            1. GH_TOKEN 없으면 푸시(github에 내용 올리기) 불가
            1. Settings가 안보인다면
                <details>
                <summary>해결 방법</summary>

                    상단 탭의 가장 오른쪽의 … 버튼 클릭

                    또는 전체 화면으로 변경

                </details>

        1. 로드맵
            1. 기능
                1. 블록 중 코드 내부 텍스트 변환하기
                1. Notion에 자주 작성하는 시간을 제외한 시간 중, 가장 이른 시간과 Notion 작성하는 시간이 가장 빠른 시간 사이의 Notion작성하는 시간이 가장 빠른 시간 1시간 전에 스크립트 실행
            1. 문의 연락
                1. ramgthunder12@gmail.com
        1. 기여 가이드
        1. 라이센스
            1. 아파치 2.0
    </details>

    <details>
    <summary>작성 내용 프로토타입</summary>

        # 📦 Notion to GitHub Sync

        Notion Database 페이지를 GitHub 저장소에 Markdown 형식으로 자동 백업하는 도구입니다.
GitHub Actions를 이용해 매일 정해진 시간에 자동 실행되며, 수동 실행도 지원합니다.

        ## 📝 프로젝트 소개

        - 설명
이 프로젝트는 Notion Database → GitHub Markdown 자동 백업 도구입니다.
        - 만든 계기
            이 프로젝트는 Notion에 작성한 글을 GitHub에도 다시 작성해야 하는 이중 작업 때문에 발생하는 시간과 비용 낭비 문제를 해결하기 위해 만들어졌습니다.

        ## ✨ 기능

    </details>

    - [ ] .env 설정(데이터 베이스 이름 설정)
    - [ ] secrit variavles 설정
    - [ ] Github token 설정
    - [ ] github actions 설정
    - [ ] clone 방법
    - [ ] NotionAPI 설정
    - [ ] 시간 설정
    - [ ] 추가 기능 사항
    - [ ] 애러 사항 문의
    - [ ] 지원 환경
- [ ] 다른 사람도 사용할 수 있도록 정리 하기
- [ ] rich_text 색상 속성 추가 고민 하기
- [ ] 블록 속성 추가하기
    - [x] 토글 추가 하기
    - [x] 테이블 추가 하기
        - [ ] 테이블 변환 방식 바꾸기
    - [ ] 코드 블럭 내용물 처리하기
- [ ] 사용자 커밋 시간에 따른 시간 설정
- [ ] 하위 블록의 하위 블록 반영하기
## 배울점
