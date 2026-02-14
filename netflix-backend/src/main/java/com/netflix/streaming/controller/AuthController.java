package com.netflix.streaming.controller;

import com.netflix.streaming.dto.request.LoginRequest;
import com.netflix.streaming.dto.request.RegisterRequest;
import com.netflix.streaming.dto.response.AuthResponse;
import com.netflix.streaming.dto.response.UserResponse;
import com.netflix.streaming.security.UserPrincipal;
import com.netflix.streaming.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserResponse response = authService.getCurrentUser(userPrincipal.getId());
        return ResponseEntity.ok(response);
    }
}
