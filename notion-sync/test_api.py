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

        # Toggle
        elif btype == "toggle":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}<details>\n{indent_str}<summary>{text}</summary>\n")
            if block.get("has_children"):
                children = get_block_children(block["id"])
                md_lines.append(blocks_to_markdown(children, indent + 1))
            md_lines.append(f"{indent_str}</details>\n")

        # Table
        elif btype == "table":
            # 테이블 블록은 children 안에 table_row 블록들이 있음
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
                    # 헤더 구분선 추가 (첫 번째 줄 뒤)
                    if i == 0:
                        table_lines.append("|" + "|".join([" --- "]*len(cells)) + "|")
            md_lines.extend(table_lines)

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
    with open("notion_page_test3.md", "w", encoding="utf-8") as f:
        f.write(markdown_text)

    print("✅ 변환 완료! notion_page_test3.md 파일 확인하세요.")
