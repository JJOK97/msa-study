package com.example.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 사용자 서비스 메인 클래스
 * 
 * 이 서비스는:
 * 1. HTTP REST API 제공 (포트 8081)
 * 2. gRPC 서버 제공 (포트 50051)
 * 3. 다른 서비스와 gRPC로 통신
 */
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
} 