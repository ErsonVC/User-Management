package com.backend.user_management.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse { //Respuesta del usuario

    private Long id;
    private String name;
    private String email;
    private Boolean active;
    //una forma mas personalizada de mostrar datos del LocalDareTime
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") = "29/01/2026 12:31" y no  "2026-01-29T12:31:53.7352637"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
