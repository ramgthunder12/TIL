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
        </details>

    - 인증은 Bearer Token 사용
        <details>
        <summary>Bearer Token</summary>

            API 요청 시 인증을 위해 HTTP 헤더에 넣는 토큰

            OAuth 2.0 인증 체계에서 쓰이는 방식 중 하나

            - [ ] 왜 사용할까?
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
- [ ] 다른 사람도 사용할 수 있도록 정리 하기
- [ ] rich_text 색상 속성 추가 고민 하기
- [ ] 블록 속성 추가하기
- [ ] 사용자 커밋 시간에 따른 시간 설정
## 배울점
