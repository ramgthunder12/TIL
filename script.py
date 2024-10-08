import os
import requests
import json
import shutil
import smtplib
from datetime import datetime
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

# 환경 변수에서 Notion API 키와 데이터베이스 ID 가져오기
notion_api_key = os.getenv('NOTION_API_KEY')
database_id = '7de93cbc1636434086efaec8ba184ff4'  # 자신의 Notion 데이터베이스 ID로 교체

# 이메일 설정
SENDER_EMAIL = os.getenv('s333o333s@naver.com')
RECEIVER_EMAIL = os.getenv('ramgthunder12@gmail.com')
EMAIL_PASSWORD = os.getenv('Voalffl486^^')

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

# 이메일 알림 보내기
def send_email_notification(message):
    msg = MIMEMultipart()
    msg['From'] = SENDER_EMAIL
    msg['To'] = RECEIVER_EMAIL
    msg['Subject'] = 'Git Merge Conflict Alert'
    msg.attach(MIMEText(message, 'plain'))
    
    # 네이버 메일 SMTP 서버 정보
    with smtplib.SMTP('smtp.naver.com', 587) as server:
        server.starttls()  # TLS 암호화 시작
        server.login(SENDER_EMAIL, EMAIL_PASSWORD)
        server.sendmail(SENDER_EMAIL, RECEIVER_EMAIL, msg.as_string())

# 데이터 저장 및 포맷팅
def save_data_to_file(data):
    # 데이터 파일명
    filename = "notion_data.json"
    conflict_folder = "충돌전"
    os.makedirs(conflict_folder, exist_ok=True)
    
    # 파일 존재 여부 체크
    if os.path.exists(filename):
        # 기존 데이터를 백업
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        backup_filename = os.path.join(conflict_folder, f"notion_data_{timestamp}.json")
        shutil.copyfile(filename, backup_filename)
        
        # 기존 파일과 새로운 데이터를 비교
        with open(filename, "r") as file:
            try:
                existing_data = json.load(file)
            except json.JSONDecodeError:
                # 충돌 발생 시
                send_email_notification("Merge conflict detected. Old data backed up.")
                existing_data = {}

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
