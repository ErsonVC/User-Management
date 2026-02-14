package com.backend.user_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest { // Solicitud de actualización de usuario

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Size(max = 150, message = "El email no puede superar los 150 caracteres")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "El estado activo es obligatorio")
    private boolean active;
}
