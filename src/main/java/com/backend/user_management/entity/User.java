package com.backend.user_management.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Persistent;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean active;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "update_at")
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;

    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();

    }


}
