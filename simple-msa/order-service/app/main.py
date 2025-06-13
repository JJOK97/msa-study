from fastapi import FastAPI
import grpc
import user_pb2
import user_pb2_grpc
from pydantic import BaseModel
from typing import List

app = FastAPI(title="Order Service")

# gRPC 클라이언트 설정 (Java 서비스와 통신)
def get_user_from_java_service(user_id: int):
    """Java user-service에서 사용자 정보를 가져옵니다"""
    try:
        # gRPC 채널 생성
        channel = grpc.insecure_channel('localhost:50051')
        stub = user_pb2_grpc.UserServiceStub(channel)
        
        # 요청 생성
        request = user_pb2.UserRequest(user_id=user_id)
        
        # gRPC 호출
        response = stub.GetUser(request)
        
        return {
            "user_id": response.user_id,
            "username": response.username,
            "email": response.email,
            "created_at": response.created_at
        }
    except Exception as e:
        return {"error": f"사용자 정보를 가져올 수 없습니다: {str(e)}"}

class OrderRequest(BaseModel):
    user_id: int
    items: List[str]
    total_amount: float

@app.get("/")
def read_root():
    return {"message": "Order Service가 실행 중입니다!"}

@app.get("/api/orders/{order_id}")
def get_order(order_id: int):
    """주문 정보 조회"""
    return {
        "order_id": order_id,
        "user_id": 1,
        "items": ["상품1", "상품2"],
        "total_amount": 50000,
        "status": "배송중",
        "created_at": "2024-01-01"
    }

@app.post("/api/orders")
def create_order(order: OrderRequest):
    """주문 생성 - Java 서비스에서 사용자 정보를 가져옵니다"""
    
    # Java user-service에서 사용자 정보 조회
    user_info = get_user_from_java_service(order.user_id)
    
    if "error" in user_info:
        return {"error": user_info["error"]}
    
    # 주문 생성
    new_order = {
        "order_id": 123,
        "user_info": user_info,  # Java 서비스에서 가져온 사용자 정보
        "items": order.items,
        "total_amount": order.total_amount,
        "status": "주문완료",
        "created_at": "2024-01-01"
    }
    
    return new_order

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8082) 