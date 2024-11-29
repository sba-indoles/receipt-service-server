from locust import HttpUser, task, between, events
import random
import string

# 각 서버의 호스트 설정
MEMBER_HOST = "http://localhost:7070"
RECEIPT_HOST = "http://localhost:9090"

def generate_user_id():
    """고유한 사용자 ID를 생성하는 함수"""
    return ''.join(random.choices(string.ascii_letters + string.digits, k=8))

def generate_password():
    """유효한 비밀번호를 생성하는 함수 (영문자와 숫자 포함)"""
    lowercase = random.choice(string.ascii_lowercase)
    uppercase = random.choice(string.ascii_uppercase)
    digit = random.choice(string.digits)
    remaining_length = 12 - 3
    remaining_characters = ''.join(random.choices(string.ascii_letters + string.digits, k=remaining_length))
    password = lowercase + uppercase + digit + remaining_characters
    return ''.join(random.sample(password, len(password)))

class UserBehavior(HttpUser):
    wait_time = between(1, 3)

    def on_start(self):
        """회원가입 후 로그인 및 거래 내역 조회를 위한 초기 설정"""
        self.user_id = generate_user_id()
        self.password = generate_password()
        self.access_token = None
        self.signup_successful = False

    @task(1)  # 회원가입 API 호출
    def signup(self):
        signup_data = {
            "signUpId": self.user_id,
            "password": self.password,
            "userRole": "BUYER",
        }
        with self.client.post(f"{MEMBER_HOST}/members/signup", json=signup_data, headers={"Content-Type": "application/json"}, catch_response=True) as response:
            if response.status_code == 400:
                print(f"Signup failed for {self.user_id}: {response.text}")
                self.user_id = generate_user_id()  # 새로운 ID 생성
                self.signup()  # 재시도
            elif response.status_code == 200:
                print(f"Signup successful for {self.user_id}: {response.text}")
                self.signup_successful = True
                self.signin()  # 회원가입 후 즉시 로그인 시도

    @task(2)  # 로그인 API 호출
    def signin(self):
        if not self.signup_successful:
            return

        signin_data = {
            "signInId": self.user_id,
            "password": self.password
        }
        with self.client.post(f"{MEMBER_HOST}/members/signin", json=signin_data, headers={"Content-Type": "application/json"}, catch_response=True) as response:
            if response.status_code == 200:
                self.access_token = response.json().get("accessToken")
                print(f"Signin successful for {self.user_id}: {response.text}")
                self.get_receipts()  # 로그인 후 거래 내역 조회
            else:
                print(f"Signin failed for {self.user_id}: {response.text}")

    @task(3)  # 거래 내역 조회 API 호출
    def get_receipts(self):
        if not self.access_token:
            print("Access token is missing. Cannot retrieve receipts.")
            return

        headers = {"Authorization": f"Bearer {self.access_token}"}
        # offset과 size 파라미터 추가
        params = {"offset": 0, "size": 10}
        with self.client.get(f"{RECEIPT_HOST}/receipts/buyer", headers=headers, params=params, catch_response=True) as response:
            if response.status_code == 200:
                print(f"Receipts retrieved successfully: {response.text}")
            else:
                print(f"Failed to retrieve receipts: {response.status_code}, Response: {response.text}")

    @task(4)  # 거래 내역 상세 조회 API 호출
    def get_receipt_detail(self):
        receipt_id = "44b0d7af-f789-483a-80dc-51f8efca31e6"
        if not self.access_token:
            print("Access token is missing. Cannot retrieve receipt details.")
            return

        headers = {"Authorization": f"Bearer {self.access_token}"}
        with self.client.get(f"{RECEIPT_HOST}/receipts/{receipt_id}", headers=headers, catch_response=True) as response:
            if response.status_code == 200:
                print(f"Receipt details retrieved successfully: {response.text}")
            else:
                print(f"Failed to retrieve receipt details: {response.status_code}, Response: {response.text}")

    @task(5)  # 거래 내역 환불 API 호출
    def process_refund(self):
        receipt_id = "44b0d7af-f789-483a-80dc-51f8efca31e6"
        if not self.access_token:
            print("Access token is missing. Cannot process refund.")
            return

        headers = {
            "Authorization": f"Bearer {self.access_token}",
            "Content-Type": "application/json"
        }
        refund_data = {
            "signInfoRequest": {
                "id": self.user_id,
                "Role": "BUYER"
            }
        }
        with self.client.put(f"{RECEIPT_HOST}/receipts/refund/{receipt_id}", json=refund_data, headers=headers, catch_response=True) as response:
            if response.status_code == 200:
                print(f"Refund processed successfully for receipt ID {receipt_id}: {response.text}")
            else:
                print(f"Failed to process refund: {response.status_code}, Response: {response.text}")

@events.request.add_listener
def request_handler(request_type, name, response_time, response_length, response, exception, **kwargs):
    if exception:
        print(f"Request to {name} failed with exception: {exception}")
    else:
        print(f"Successfully made a request to: {name} with response time: {response_time}ms")
