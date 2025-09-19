import os
import re
import json
import requests
import smtplib
from dotenv import load_dotenv
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

# ---------------------------
# 환경 변수 로드
# ---------------------------
load_dotenv()
NOTION_API_KEY = os.getenv("NOTION_API_KEY")
DATABASE_ID = os.getenv("DATABASE_ID")

SENDER_EMAIL = os.getenv("SENDER_EMAIL")
RECEIVER_EMAIL = os.getenv("RECEIVER_EMAIL")
EMAIL_PASSWORD = os.getenv("EMAIL_PASSWORD")

# 파일 저장 위치 (.env에서 관리)
OUTPUT_DIR = os.getenv("OUTPUT_DIR", ".")
os.makedirs(OUTPUT_DIR, exist_ok=True)

headers = {
    "Authorization": f"Bearer {NOTION_API_KEY}",
    "Content-Type": "application/json",
    "Notion-Version": "2022-06-28"
}

# ---------------------------
# 이메일 알림
# ---------------------------
def send_email_notification(message: str):
    msg = MIMEMultipart()
    msg["From"] = SENDER_EMAIL
    msg["To"] = RECEIVER_EMAIL
    msg["Subject"] = "🚨 Notion Sync Script Error"
    msg.attach(MIMEText(message, "plain"))

    try:
        with smtplib.SMTP("smtp.naver.com", 587) as server:
            server.starttls()
            server.login(SENDER_EMAIL, EMAIL_PASSWORD)
            server.sendmail(SENDER_EMAIL, RECEIVER_EMAIL, msg.as_string())
    except Exception as e:
        print(f"[WARN] 이메일 전송 실패: {e}")

# ---------------------------
# 유틸 함수
# ---------------------------
def remove_merge_conflicts(data_str: str) -> str:
    """Git merge conflict markers 제거"""
    while "<<<<<<<" in data_str and "=======" in data_str and ">>>>>>>" in data_str:
        start = data_str.find("<<<<<<<")
        mid = data_str.find("=======", start)
        end = data_str.find(">>>>>>>", mid)

        if start != -1 and mid != -1 and end != -1:
            data_str = data_str[:start] + data_str[mid + 7:end] + data_str[end + 7:]
        else:
            break
    return data_str

def safe_filename(name: str) -> str:
    """파일명으로 사용할 수 있도록 문자열 정리"""
    name = re.sub(r'[\\/*?:"<>|]', "_", name)
    return name.strip() or "untitled"

# ---------------------------
# Notion API
# ---------------------------
def get_database_pages(database_id):
    url = f"https://api.notion.com/v1/databases/{database_id}/query"
    all_results, next_cursor = [], None

    while True:
        body = {"page_size": 100}
        if next_cursor:
            body["start_cursor"] = next_cursor

        res = requests.post(url, headers=headers, json=body)
        res.raise_for_status()
        data = res.json()

        # 충돌 마커 검사
        data_str = json.dumps(data)
        if "<<<<<<<" in data_str:
            print("⚠️ Merge conflict detected in database data")
            data_str = remove_merge_conflicts(data_str)
            try:
                data = json.loads(data_str)
            except json.JSONDecodeError:
                send_email_notification("JSON decoding failed after conflict removal.")
                return {"object": "list", "results": []}

        all_results.extend(data.get("results", []))
        if not data.get("has_more"):
            break
        next_cursor = data.get("next_cursor")

    return {"object": "list", "results": all_results}

def get_block_children(block_id):
    url = f"https://api.notion.com/v1/blocks/{block_id}/children"
    all_results, next_cursor = [], None

    while True:
        params = {"page_size": 100}
        if next_cursor:
            params["start_cursor"] = next_cursor

        res = requests.get(url, headers=headers, params=params)
        res.raise_for_status()
        data = res.json()

        # 충돌 마커 검사
        data_str = json.dumps(data)
        if "<<<<<<<" in data_str:
            print("⚠️ Merge conflict detected in block data")
            data_str = remove_merge_conflicts(data_str)
            try:
                data = json.loads(data_str)
            except json.JSONDecodeError:
                send_email_notification("JSON decoding failed after conflict removal.")
                return {"object": "list", "results": []}

        all_results.extend(data.get("results", []))
        if not data.get("has_more"):
            break
        next_cursor = data.get("next_cursor")

    return {"object": "list", "results": all_results}

# ---------------------------
# Markdown 변환
# ---------------------------
def rich_text_to_markdown(rich_text_array):
    parts = []
    for rt in rich_text_array:
        text = rt.get("plain_text", "")
        ann = rt.get("annotations", {})
        if ann.get("bold"): text = f"**{text}**"
        if ann.get("italic"): text = f"*{text}*"
        if ann.get("code"): text = f"`{text}`"
        if ann.get("strikethrough"): text = f"~~{text}~~"
        if ann.get("underline"): text = f"<u>{text}</u>"
        parts.append(text)
    return "".join(parts)

def blocks_to_markdown(blocks_json, indent=0):
    md_lines, indent_str = [], "    " * indent

    for block in blocks_json.get("results", []):
        btype, data = block["type"], block[block["type"]]

        # 기본 블록 처리
        if btype == "heading_1":
            md_lines.append(f"{indent_str}# {rich_text_to_markdown(data.get('rich_text', []))}\n")
        elif btype == "heading_2":
            md_lines.append(f"{indent_str}## {rich_text_to_markdown(data.get('rich_text', []))}\n")
        elif btype == "heading_3":
            md_lines.append(f"{indent_str}### {rich_text_to_markdown(data.get('rich_text', []))}\n")
        elif btype == "paragraph":
            text = rich_text_to_markdown(data.get("rich_text", []))
            if text.strip():
                md_lines.append(f"{indent_str}{text}\n")
        elif btype == "bulleted_list_item":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}- {text}")
        elif btype == "numbered_list_item":
            text = rich_text_to_markdown(data.get("rich_text", []))
            md_lines.append(f"{indent_str}1. {text}")
        elif btype == "to_do":
            checked = "x" if data.get("checked") else " "
            text = rich_text_to_markdown(data.get("rich_text", []))
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
            # 토글 안 children은 반드시 재귀 실행 후 닫기 태그
            if block.get("has_children"):
                md_lines.append(blocks_to_markdown(get_block_children(block["id"]), indent + 1))
            md_lines.append(f"{indent_str}</details>\n")
            continue  # 아래 공통 처리에서 또 실행되지 않도록 건너뜀

        # ✅ 공통 children 처리 (모든 블록에 적용)
        if block.get("has_children") and btype != "toggle":
            md_lines.append(blocks_to_markdown(get_block_children(block["id"]), indent + 1))

    return "\n".join(md_lines)

# ---------------------------
# 실행부
# ---------------------------
def export_all_pages(database_id, output_dir=OUTPUT_DIR):
    db_data = get_database_pages(database_id)
    pages = db_data.get("results", [])
    print(f"총 {len(pages)}개의 페이지 변환 시작...")

    for i, page in enumerate(pages, start=1):
        page_id, props = page["id"], page.get("properties", {})
        title_prop = None
        for prop in props.values():
            if prop["type"] == "title":
                title_prop = prop["title"]
                break
        title = "".join([t.get("plain_text", "") for t in title_prop]) if title_prop else f"page_{i}"
        filename = safe_filename(title) + ".md"

        blocks = get_block_children(page_id)
        markdown_text = blocks_to_markdown(blocks)

        filepath = os.path.join(output_dir, filename)
        with open(filepath, "w", encoding="utf-8") as f:
            f.write(markdown_text)

        print(f"✅ {title} → {filepath}")

    print("🎉 모든 페이지 변환 완료!")

if __name__ == "__main__":
    try:
        export_all_pages(DATABASE_ID)
    except Exception as e:
        error_message = f"🚨 Notion Sync Script Error\n\n{type(e).__name__}: {str(e)}"
        print(error_message)
        send_email_notification(error_message)
        raise  # 에러 발생 시 종료
