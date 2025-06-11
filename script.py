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
database_id = os.getenv('DATABASE_ID')

# 이메일 설정
SENDER_EMAIL = os.getenv('SENDER_EMAIL')
RECEIVER_EMAIL = os.getenv('RECEIVER_EMAIL')
EMAIL_PASSWORD = os.getenv('EMAIL_PASSWORD')

# Notion API 설정
headers = {
    "Authorization": f"Bearer {notion_api_key}",
    "Content-Type": "application/json",
    "Notion-Version": "2022-06-28"
}

# Notion 데이터베이스 가져오기 첫번째 블록
# 블록 가져오기, 블록 아이디, 시작커서, 페이지크기, 헤더
# url = 'https://api.notion.com/v1/blocks/{block_id}/children?page_size=100'
# page_size 는 어떻게 구하지?
def get_notion_data():
    url = f"https://api.notion.com/v1/databases/{database_id}/query"
    response = requests.post(url, headers=headers)
    data = response.json()
    
    return data

def get_block_children(children_id):
    url = f"https://api.notion.com/v1/blocks/{children_id}/children?page_size=100"
    response = requests.get(url, headers=headers)
    data = response.json()
    
    return data
# Notion 데이터베이스 에러 응답 검사
def is_notion_error(data: dict) -> bool:
    return data.get("object") == "error"


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

# 충돌 마커가 포함된 데이터를 정리하는 함수
def remove_merge_conflicts(data_str):
    """ Git 머지 충돌 마커가 포함된 데이터를 정리 """
    while "<<<<<<<" in data_str and "=======" in data_str and ">>>>>>>" in data_str:
        start = data_str.find("<<<<<<<")
        mid = data_str.find("=======", start)
        end = data_str.find(">>>>>>>", mid)

        if start != -1 and mid != -1 and end != -1:
            # 상위 버전 부분은 버리고, 중간 이후 버전만 남김
            data_str = data_str[:start] + data_str[mid + len("======="):end] + data_str[end + len(">>>>>>>"):]
    
    return data_str

def extract_title_from_page(page):
    title_property = page.get("properties", {}).get("Name", {})
    title_array = title_property.get("title", [])
    if title_array and isinstance(title_array[0], dict):
        return title_array[0].get("plain_text", "")
    return ""

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
                existing_data = file.read()
                
                # 충돌 마커가 있는지 확인
                if "<<<<<<<" in existing_data:
                    print("Merge conflict detected, removing conflict markers.")
                    cleaned_data = remove_merge_conflicts(existing_data)
                    
                    # 충돌 마커를 제거한 데이터를 다시 로드
                    try:
                        existing_data = json.loads(cleaned_data)
                    except json.JSONDecodeError:
                        # 오류 발생 시 이메일 알림 및 로그 기록
                        send_email_notification("JSON decoding failed after conflict marker removal.")
                        existing_data = {}
                else:
                    # JSON 로드 시도
                    existing_data = json.loads(existing_data)
            except json.JSONDecodeError as e:
                # 충돌 발생 시 이메일 알림 및 로그 기록
                error_message = f"JSONDecodeError: {str(e)}\nMerge conflict detected. Old data backed up."
                print(error_message)  # 콘솔에 오류 출력
                send_email_notification(error_message)
                existing_data = {}
            except Exception as e:
                # 예상치 못한 예외 처리
                error_message = f"Unexpected error: {str(e)}"
                print(error_message)
                send_email_notification(error_message)
                existing_data = {}

    else:
        existing_data = {}

    # 기존 데이터와 비교하여 변경 사항 확인
    if existing_data != data:
        with open(filename, "w") as file:
            json.dump(data, file, indent=4)
        return True  # 변경 사항 있음
    return False  # 변경 사항 없음


# 데이터 저장 및 포맷팅
def save_block_to_file(blocks, page_title, page_id):
    # 데이터 파일명
    # block_data에서 이름 찾기
    os.makedirs("NotionTIL", exist_ok=True)
    filename = f"NotionTIL/{page_title or page_id}.json"

    conflict_folder = "충돌전"
    os.makedirs(conflict_folder, exist_ok=True)
    
    # 파일 존재 여부 체크
    if os.path.exists(filename):
        # 기존 데이터를 백업
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")

        conflict_path = os.path.join(conflict_folder, "NotionTIL_conflict")
        os.makedirs(conflict_path, exist_ok=True)
        backup_filename = os.path.join(conflict_path, f"{page_title}_{timestamp}_{page_id[:6]}.json")

        shutil.copyfile(filename, backup_filename)
        
        # 기존 파일과 새로운 데이터를 비교
        with open(filename, "r") as file:
            try:
                existing_data = file.read()
                
                # 충돌 마커가 있는지 확인
                if "<<<<<<<" in existing_data:
                    print("Merge conflict detected, removing conflict markers.")
                    cleaned_data = remove_merge_conflicts(existing_data)
                    
                    # 충돌 마커를 제거한 데이터를 다시 로드
                    try:
                        existing_data = json.loads(cleaned_data)
                    except json.JSONDecodeError:
                        # 오류 발생 시 이메일 알림 및 로그 기록
                        send_email_notification("JSON decoding failed after conflict marker removal.")
                        existing_data = {}
                else:
                    # JSON 로드 시도
                    existing_data = json.loads(existing_data)
            except json.JSONDecodeError as e:
                # 충돌 발생 시 이메일 알림 및 로그 기록
                error_message = f"JSONDecodeError: {str(e)}\nMerge conflict detected. Old data backed up."
                print(error_message)  # 콘솔에 오류 출력
                send_email_notification(error_message)
                existing_data = {}
            except Exception as e:
                # 예상치 못한 예외 처리
                error_message = f"Unexpected error: {str(e)}"
                print(error_message)
                send_email_notification(error_message)
                existing_data = {}

    else:
        existing_data = {}
    
    # 기존 데이터와 비교하여 변경 사항 확인
    if existing_data != blocks:
        with open(filename, "w") as file:
            json.dump(blocks, file, indent=4)
        return True  # 변경 사항 있음
    return False  # 변경 사항 없음

if __name__ == "__main__":
    notion_data = get_notion_data()  # 데이터 가져오기
    changed = save_data_to_file(notion_data)
    if changed:
        print("Data updated, committing changes.")
    else:
        print("No changes detected, skipping commit.")
    
    # 모든 자식 블록 가져오기
    for page in notion_data.get("results", []):
        page_id = page.get("id", "")
        title = extract_title_from_page(page)
        # 값 있는지 확인
        if page_id:
            # 자식 블럭 요청하기
            # 있으면 파일만들기, 없으면 종료
            blocks = get_block_children(page_id).get("results", [])
            if blocks:
            # 자식 블록 파일로 만들기
                save_block_to_file(blocks, title, page_id)
            else:
                print("None children file.")
        else:
            print("No childere ID found.")
