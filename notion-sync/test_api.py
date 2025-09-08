import os
import re
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
    """데이터베이스 안의 모든 페이지 목록 가져오기 (pagination 지원)"""
    url = f"https://api.notion.com/v1/databases/{database_id}/query"
    all_results = []
    next_cursor = None

    while True:
        body = {"page_size": 100}
        if next_cursor:
            body["start_cursor"] = next_cursor

        res = requests.post(url, headers=headers, json=body)
        res.raise_for_status()
        data = res.json()

        all_results.extend(data.get("results", []))

        if not data.get("has_more"):
            break
        next_cursor = data.get("next_cursor")

    return {"object": "list", "results": all_results}

def get_block_children(block_id):
    """해당 블록(children) 모두 가져오기 (pagination 지원)"""
    url = f"https://api.notion.com/v1/blocks/{block_id}/children"
    all_results = []
    next_cursor = None

    while True:
        params = {"page_size": 100}
        if next_cursor:
            params["start_cursor"] = next_cursor

        res = requests.get(url, headers=headers, params=params)
        res.raise_for_status()
        data = res.json()

        all_results.extend(data.get("results", []))

        if not data.get("has_more"):
            break
        next_cursor = data.get("next_cursor")

    return {"object": "list", "results": all_results}

# ---------------------------
# Markdown 변환 함수
# ---------------------------
def rich_text_to_markdown(rich_text_array):
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
    md_lines = []
    indent_str = "    " * indent

    for block in blocks_json.get("results", []):
        btype = block["type"]
        data = block[btype]

        if btype == "heading_1":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}# {text}\n")

        elif btype == "heading_2":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}## {text}\n")

        elif btype == "heading_3":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}### {text}\n")

        elif btype == "paragraph":
            text = rich_text_to_markdown(data.get("rich_text", []))
            if text.strip():
                md_lines.append(f"{indent_str}{text}\n")

        elif btype == "bulleted_list_item":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}- {text}")
            if block.get("has_children"):
                children = get_block_children(block["id"])
                md_lines.append(blocks_to_markdown(children, indent + 1))

        elif btype == "numbered_list_item":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}1. {text}")
            if block.get("has_children"):
                children = get_block_children(block["id"])
                md_lines.append(blocks_to_markdown(children, indent + 1))

        elif btype == "to_do":
            text = rich_text_to_markdown(data.get("rich_text", []))
            checked = "x" if data.get("checked") else " "
            md_lines.append(f"{indent_str}- [{checked}] {text}")

        elif btype == "quote":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}> {text}")

        elif btype == "code":
            lang = data.get("language", "")
            text = "".join([rt.get("plain_text", "") for rt in data.get("rich_text", [])])
            md_lines.append(f"{indent_str}```{lang}\n{text}\n```")

        elif btype == "toggle":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}<details>\n{indent_str}<summary>{text}</summary>\n")
            if block.get("has_children"):
                children = get_block_children(block["id"])
                md_lines.append(blocks_to_markdown(children, indent + 1))
            md_lines.append(f"{indent_str}</details>\n")

        elif btype == "table":
            rows = get_block_children(block["id"])
            table_lines = []
            for i, row in enumerate(rows.get("results", [])):
                if row["type"] == "table_row":
                    cells = row["table_row"]["cells"]
                    row_texts = []
                    for cell in cells:
                        cell_text = rich_text_to_markdown(cell)
                        row_texts.append(cell_text)
                    line = "| " + " | ".join(row_texts) + " |"
                    table_lines.append(line)
                    if i == 0:
                        table_lines.append("|" + "|".join([" --- "]*len(cells)) + "|")
            md_lines.extend(table_lines)

    return "\n".join(md_lines)

# ---------------------------
# 유틸: 파일명 안전하게 변환
# ---------------------------
def safe_filename(name):
    """파일명으로 사용할 수 있도록 문자열 정리"""
    name = re.sub(r'[\\/*?:"<>|]', "_", name)  # 파일 불가 문자 제거
    return name.strip() or "untitled"

# ---------------------------
# 새로운 메소드: 모든 페이지 변환
# ---------------------------
def export_all_pages(database_id, output_dir="."):
    """데이터베이스의 모든 페이지를 Markdown 파일로 변환 (제목 기반 파일명)"""
    db_data = get_database_pages(database_id)
    pages = db_data.get("results", [])

    print(f"총 {len(pages)}개의 페이지를 변환합니다...")

    for i, page in enumerate(pages, start=1):
        page_id = page["id"]

        # 제목 추출 (title 프로퍼티는 DB 설정에 따라 이름이 다름 → 보통 "Name")
        props = page.get("properties", {})
        title_prop = None
        for prop in props.values():
            if prop["type"] == "title":
                title_prop = prop["title"]
                break
        if title_prop:
            title = "".join([t.get("plain_text", "") for t in title_prop])
        else:
            title = f"page_{i}"

        filename = safe_filename(title) + ".md"
        print(f"[{i}] {title} → {filename}")

        # 블록 가져오기
        blocks = get_block_children(page_id)
        markdown_text = blocks_to_markdown(blocks)

        # 저장
        filepath = os.path.join(output_dir, filename)
        with open(filepath, "w", encoding="utf-8") as f:
            f.write(markdown_text)

        print(f"✅ 변환 완료! {filepath} 파일 생성됨")

    print("🎉 모든 페이지 변환 완료!")

# ---------------------------
# 실행부
# ---------------------------
if __name__ == "__main__":
    export_all_pages(DATABASE_ID)
