# 간단한 MSA 아키텍처 예제

이 프로젝트는 **Java(Spring Boot)**와 **Python(FastAPI)**를 사용한 마이크로서비스 아키텍처의 간단한 예제입니다.

## 🏗️ 아키텍처 구조

```
┌─────────────────┐    HTTP     ┌─────────────────┐
│   프론트엔드      │ ────────► │  user-service   │
│                 │            │   (Java)        │
└─────────────────┘            │   포트: 8081     │
                               └─────────────────┘
                                        │
                                        │ gRPC
                                        │ 포트: 50051
                                        ▼
┌─────────────────┐    HTTP     ┌─────────────────┐
│   프론트엔드      │ ────────► │ order-service   │
│                 │            │   (Python)      │
└─────────────────┘            │   포트: 8082     │
                               └─────────────────┘
```

## 📋 서비스 설명

### 1. user-service (Java + Spring Boot)

-   **포트**: 8081 (HTTP), 50051 (gRPC)
-   **역할**: 사용자 정보 관리
-   **기술**: Spring Boot, gRPC Server, H2 Database
-   **API 예제**:
    -   `GET /api/users/1` - 사용자 조회
    -   `POST /api/users` - 사용자 생성

### 2. order-service (Python + FastAPI)

-   **포트**: 8082 (HTTP)
-   **역할**: 주문 정보 관리
-   **기술**: FastAPI, gRPC Client
-   **API 예제**:
    -   `GET /api/orders/1` - 주문 조회
    -   `POST /api/orders` - 주문 생성 (user-service에서 사용자 정보 조회)

## 🔄 데이터 흐름

1. **프론트엔드 → order-service**: 주문 생성 요청
2. **order-service → user-service**: gRPC로 사용자 정보 조회
3. **user-service → order-service**: 사용자 정보 응답
4. **order-service → 프론트엔드**: 주문 생성 완료 응답

## 🚀 실행 방법

### 1. user-service (Java) 실행

```bash
cd user-service
./gradlew bootRun
```

### 2. order-service (Python) 실행

```bash
cd order-service
pip install -r requirements.txt
python app/main.py
```

### 3. Proto 파일 컴파일 (필요시)

```bash
# Python용 proto 파일 생성
python -m grpc_tools.protoc -I../proto --python_out=. --grpc_python_out=. ../proto/user.proto
```

## 🧪 테스트

### user-service 테스트

```bash
# 사용자 조회
curl http://localhost:8081/api/users/1

# 사용자 생성
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "홍길동", "email": "hong@example.com"}'
```

### order-service 테스트

```bash
# 주문 조회
curl http://localhost:8082/api/orders/1

# 주문 생성 (user-service와 gRPC 통신)
curl -X POST http://localhost:8082/api/orders \
  -H "Content-Type: application/json" \
  -d '{"user_id": 1, "items": ["상품1", "상품2"], "total_amount": 50000}'
```

## 🔧 주요 특징

-   **언어 간 통신**: Java ↔ Python gRPC 통신
-   **독립적 배포**: 각 서비스는 독립적으로 실행 가능
-   **타입 안전성**: Proto 파일로 API 스키마 정의
-   **확장성**: 새로운 서비스 추가 용이

## 📁 프로젝트 구조

```
simple-msa/
├── proto/
│   └── user.proto              # gRPC 스키마 정의
├── user-service/               # Java 서비스
│   ├── build.gradle
│   └── src/main/
│       ├── java/com/example/user/
│       │   ├── UserServiceApplication.java
│       │   └── UserController.java
│       └── resources/
│           └── application.yml
└── order-service/              # Python 서비스
    ├── requirements.txt
    └── app/
        └── main.py
```
