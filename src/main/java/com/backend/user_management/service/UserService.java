package com.backend.user_management.service;

import com.backend.user_management.dto.UserCreateRequest;
import com.backend.user_management.dto.UserResponse;
import com.backend.user_management.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);

    Page<UserResponse> getUsersPaged(int page, int size);
}
