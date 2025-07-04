package com.radiuk.movieshelfbackendcore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "registered_at")
    private OffsetDateTime registeredAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "avatar_url")
    private String avatarUrl;

    public enum Role {
        ROLE_ADMIN, ROLE_USER
    }
}