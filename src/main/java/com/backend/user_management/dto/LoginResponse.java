package com.backend.user_management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private Long id;
    private String name;
    private String email;
    private String token;
}
