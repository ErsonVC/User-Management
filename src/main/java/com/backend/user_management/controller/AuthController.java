package com.backend.user_management.controller;

import com.backend.user_management.dto.LoginRequest;
import com.backend.user_management.dto.LoginResponse;
import com.backend.user_management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }


}
