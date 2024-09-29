import os
import requests
import json
from datetime import datetime

# 환경 변수에서 Notion API 키와 데이터베이스 ID 가져오기
notion_api_key = os.getenv('NOTION_API_KEY')
database_id = '7de93cbc1636434086efaec8ba184ff4'  # 자신의 Notion 데이터베이스 ID로 교체

# Notion API 설정
headers = {
    "Authorization": f"Bearer {notion_api_key}",
    "Content-Type": "application/json",
    "Notion-Version": "2022-06-28"
}

# Notion 데이터베이스 가져오기
def get_notion_data():
    url = f"https://api.notion.com/v1/databases/{database_id}/query"
    response = requests.post(url, headers=headers)
    data = response.json()
    return data

# 데이터 저장 및 포맷팅
def save_data_to_file(data):
    # 데이터 파일명
    filename = "notion_data.json"
    # 파일 존재 여부 체크
    if os.path.exists(filename):
        with open(filename, "r") as file:
            existing_data = json.load(file)
    else:
        existing_data = {}

    # 기존 데이터와 비교하여 변경 사항 확인
    if existing_data != data:
        with open(filename, "w") as file:
            json.dump(data, file, indent=4)
        return True  # 변경 사항 있음
    return False  # 변경 사항 없음

if __name__ == "__main__":
    notion_data = get_notion_data()
    changed = save_data_to_file(notion_data)
    if changed:
        print("Data updated, committing changes.")
    else:
        print("No changes detected, skipping commit.")
