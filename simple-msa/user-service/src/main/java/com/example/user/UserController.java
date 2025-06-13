package com.example.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

/**
 * 사용자 REST API 컨트롤러
 * 
 * 프론트엔드에서 HTTP 요청을 받아 처리합니다.
 * 예: GET http://localhost:8081/api/users/1
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long userId) {
        // 실제로는 데이터베이스에서 조회
        Map<String, Object> user = Map.of(
            "user_id", userId,
            "username", "홍길동",
            "email", "hong@example.com",
            "created_at", "2024-01-01"
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, String> request) {
        // 실제로는 데이터베이스에 저장
        Map<String, Object> user = Map.of(
            "user_id", 1L,
            "username", request.get("username"),
            "email", request.get("email"),
            "created_at", "2024-01-01"
        );
        return ResponseEntity.ok(user);
    }
} 