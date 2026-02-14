package com.backend.user_management.service;

import com.backend.user_management.dto.LoginRequest;
import com.backend.user_management.dto.UserCreateRequest;
import com.backend.user_management.dto.UserResponse;
import com.backend.user_management.dto.UserUpdateRequest;
import com.backend.user_management.entity.User;
import com.backend.user_management.exception.ResourceNotFoundException;
import com.backend.user_management.exception.BadRequestException;
import com.backend.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreateRequest request) {

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        // Regla de negocio: email único
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
            //throw new BadRequestException("El email ya está registrado");
        }


        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encryptedPassword)
                .active(true)
                .build();

        User saveUser = userRepository.save(user);

        return mapToResponse(saveUser);

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        return mapToResponse(user);
    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));

        return mapToResponse(user);
    }


    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setActive(request.isActive());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        user.setActive(false);
        userRepository.save(user);

    }

    public Page<UserResponse> getUsersPaged(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


}
