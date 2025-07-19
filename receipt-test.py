from locust import HttpUser, SequentialTaskSet, task, between, events
from datetime import datetime, timedelta
import random
import string
import time

MEMBER_HOST = "http://localhost:7070"      # 회원 서버
AUCTION_HOST = "http://localhost:8080"     # 경매 서버
RECEIPT_HOST = "http://localhost:9090"     # 거래내역 서버

def generate_user_id():
    random_part = ''.join(random.choices(string.ascii_letters + string.digits, k=8))
    timestamp_part = str(int(time.time() * 1000))
    return f"{random_part}_{timestamp_part}"

def generate_password():
    l = random.choice(string.ascii_lowercase)
    u = random.choice(string.ascii_uppercase)
    d = random.choice(string.digits)
    rest = ''.join(random.choices(string.ascii_letters + string.digits, k=9))
    return ''.join(random.sample(l + u + d + rest, 12))

class ReceiptE2EFlow(SequentialTaskSet):
    def on_start(self):
        # 1. SELLER 생성 및 경매 등록 -------------------
        self.seller_id = generate_user_id()
        self.seller_pw = generate_password()
        self.seller_token = None
        signup_seller = {"signUpId": self.seller_id, "password": self.seller_pw, "userRole": "SELLER"}
        resp1 = self.client.post(f"{MEMBER_HOST}/members/signup", json=signup_seller, headers={"Content-Type": "application/json"}, catch_response=True)
        if resp1.status_code == 200:
            signin = {"signInId": self.seller_id, "password": self.seller_pw}
            resp2 = self.client.post(f"{MEMBER_HOST}/members/signin", json=signin, headers={"Content-Type":"application/json"}, catch_response=True)
            if resp2.status_code == 200:
                self.seller_token = resp2.json().get("accessToken")

        self.auction_id = None
        if self.seller_token:
            start_time = datetime.now() + timedelta(minutes=1)
            finish_time = start_time + timedelta(minutes=60)
            auction_data = {
                "productName": "Sample Product",
                "originPrice": 10000,
                "stock": 50,
                "maximumPurchaseLimitCount": 5,
                "pricePolicy": {"type": "CONSTANT", "variationWidth": 10},
                "variationDuration": "PT1M",
                "requestTime": datetime.now().isoformat(),
                "startedAt": start_time.isoformat(),
                "finishedAt": finish_time.isoformat(),
                "isShowStock": True
            }
            seller_headers = {
                "Authorization": f"Bearer {self.seller_token}",
                "Content-Type": "application/json"
            }
            res = self.client.post(f"{AUCTION_HOST}/auctions", json=auction_data, headers=seller_headers, catch_response=True)
            if res.status_code == 200:
                self.auction_id = res.json().get("id")

        # 2. BUYER(구매자) 생성 및 경매 입찰 --------------
        self.buyer_id = generate_user_id()
        self.buyer_pw = generate_password()
        self.buyer_token = None
        signup_buyer = {"signUpId": self.buyer_id, "password": self.buyer_pw, "userRole": "BUYER"}
        resp3 = self.client.post(f"{MEMBER_HOST}/members/signup", json=signup_buyer, headers={"Content-Type": "application/json"}, catch_response=True)
        if resp3.status_code == 200:
            signin = {"signInId": self.buyer_id, "password": self.buyer_pw}
            resp4 = self.client.post(f"{MEMBER_HOST}/members/signin", json=signin, headers={"Content-Type":"application/json"}, catch_response=True)
            if resp4.status_code == 200:
                self.buyer_token = resp4.json().get("accessToken")
        
        self.receipt_id = None
        if self.buyer_token and self.auction_id:
            bid_data = {
                "price": 10000,
                "quantity": 1
            }
            buyer_headers = {
                "Authorization": f"Bearer {self.buyer_token}",
                "Content-Type": "application/json"
            }
            res = self.client.post(f"{AUCTION_HOST}/auctions/{self.auction_id}/purchase", json=bid_data, headers=buyer_headers, catch_response=True)
            if res.status_code == 200:
                self.receipt_id = res.json().get("receiptId")

    @task(1)
    def get_receipts(self):
        "거래 내역 목록(Receipt List) 조회"
        if not self.buyer_token:
            print("Buyer token missing. Cannot query receipts.")
            return
        headers = {"Authorization": f"Bearer {self.buyer_token}"}
        params = {"offset": 0, "size": 10}
        with self.client.get(f"{RECEIPT_HOST}/receipts/buyer", headers=headers, params=params, catch_response=True) as response:
            if response.status_code == 200:
                print(f"Receipts retrieved successfully: {response.text}")
            else:
                print(f"Failed to retrieve receipts: {response.status_code}, {response.text}")

    @task(2)
    def get_receipt_detail(self):
        "입찰로 생성된 실제 ReceiptID의 상세 조회"
        if not self.buyer_token or not self.receipt_id:
            print("Token or receiptID missing. Cannot query details.")
            return
        headers = {"Authorization": f"Bearer {self.buyer_token}"}
        with self.client.get(f"{RECEIPT_HOST}/receipts/{self.receipt_id}", headers=headers, catch_response=True) as response:
            if response.status_code == 200:
                print(f"Receipt detail retrieved successfully: {response.text}")
            else:
                print(f"Failed to retrieve receipt detail: {response.status_code}, {response.text}")

    @task(3)
    def process_refund(self):
        "실제 입찰 ReceiptID를 대상으로 환불"
        if not self.buyer_token or not self.receipt_id:
            print("Token or receiptID missing. Cannot refund.")
            return
        headers = {
            "Authorization": f"Bearer {self.buyer_token}",
            "Content-Type": "application/json"
        }
        refund_data = {
            "signInfoRequest": {
                "id": self.buyer_id,
                "Role": "BUYER"
            }
        }
        with self.client.put(f"{RECEIPT_HOST}/receipts/refund/{self.receipt_id}", json=refund_data, headers=headers, catch_response=True) as response:
            if response.status_code == 200:
                print(f"Refund processed successfully for receipt ID {self.receipt_id}: {response.text}")
            else:
                print(f"Failed to process refund: {response.status_code}, {response.text}")

class WebsiteUser(HttpUser):
    host = MEMBER_HOST
    tasks = [ReceiptE2EFlow]
    wait_time = between(1, 3)

@events.request.add_listener
def request_handler(request_type, name, response_time, response_length, response, exception, **kwargs):
    if exception:
        print(f"Request to {name} failed with exception: {exception}")
