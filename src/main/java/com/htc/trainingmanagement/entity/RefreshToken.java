package com.htc.trainingmanagement.entity;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

@Entity
// Marks this class as a JPA entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Maps this entity to the refresh token table
@Table(name = "665_refresh_token")
public class RefreshToken {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    // Stores the unique refresh token
    @Column(nullable = false, unique = true, length = 500)
    private String token;

    // Stores the refresh token expiration time
    @Column(nullable = false)
    private Instant expiryDate;

    // Each user can have only one active refresh token
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}