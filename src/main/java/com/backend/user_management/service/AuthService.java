package com.backend.user_management.service;

import com.backend.user_management.dto.LoginRequest;
import com.backend.user_management.dto.LoginResponse;

public interface AuthService {

    LoginResponse login (LoginRequest request);

}
