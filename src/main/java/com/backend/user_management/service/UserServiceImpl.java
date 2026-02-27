package com.backend.user_management.service;

import com.backend.user_management.dto.UserCreateRequest;
import com.backend.user_management.dto.UserResponse;
import com.backend.user_management.dto.UserUpdateRequest;
import com.backend.user_management.entity.User;
import com.backend.user_management.exception.ResourceNotFoundException;
import com.backend.user_management.exception.BadRequestException;
import com.backend.user_management.repository.UserRepository;
import com.backend.user_management.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private boolean isAdmin(User user) {
        return user.getRole() == Role.ROLE_ADMIN;
    }

    @Override
    public UserResponse createUser(UserCreateRequest request) {

        String encryptedPassword = passwordEncoder.encode(request.getPassword());


        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");

        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encryptedPassword)
                .active(true)
                .role(Role.ROLE_USER)
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

        User currentUser = getCurrentUser();

        User targetUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        if (!isAdmin(currentUser) &&
                !currentUser.getId().equals(targetUser.getId())) {

            throw new AccessDeniedException("Acceso denegado");
        }

        return mapToResponse(targetUser);
    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));

        return mapToResponse(user);
    }


    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {

        User currentUser = getCurrentUser();

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        if (!isAdmin(currentUser) &&
                !currentUser.getId().equals(user.getId())) {

            throw new AccessDeniedException("No puedes modificar otro usuario");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setActive(request.isActive());

        return mapToResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {

        User currentUser = getCurrentUser();

        if (!isAdmin(currentUser)) {
            throw new AccessDeniedException("Solo ADMIN puede eliminar usuarios");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado con id: " + id));

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
