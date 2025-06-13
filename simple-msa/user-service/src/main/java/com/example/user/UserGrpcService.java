package com.example.user;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import user.UserServiceGrpc;
import user.User.UserRequest;
import user.User.UserResponse;
import user.User.CreateUserRequest;

/**
 * 사용자 gRPC 서비스 구현체
 * 
 * Python order-service에서 이 gRPC 서버를 호출합니다.
 * 포트 50051에서 gRPC 요청을 받아 처리합니다.
 */
@GrpcService
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void getUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        // 실제로는 데이터베이스에서 조회
        UserResponse response = UserResponse.newBuilder()
            .setUserId(request.getUserId())
            .setUsername("홍길동")
            .setEmail("hong@example.com")
            .setCreatedAt("2024-01-01")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        // 실제로는 데이터베이스에 저장
        UserResponse response = UserResponse.newBuilder()
            .setUserId(1L)
            .setUsername(request.getUsername())
            .setEmail(request.getEmail())
            .setCreatedAt("2024-01-01")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
} 