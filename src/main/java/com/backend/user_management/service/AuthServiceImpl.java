package com.backend.user_management.service;

import com.backend.user_management.dto.LoginRequest;
import com.backend.user_management.dto.LoginResponse;
import com.backend.user_management.entity.User;
import com.backend.user_management.exception.BadRequestException;
import com.backend.user_management.repository.UserRepository;
import com.backend.user_management.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new BadRequestException("Credenciales Invalidas"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadRequestException("Credenciales Invalidas");
        }

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .token(token)
                .build();
    }
}
