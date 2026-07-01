package com.htc.trainingmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Role;
import com.htc.trainingmanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Checks if a user exists with the given email.
    boolean existsByEmail(String email);

    // Finds all users having the given role.
    List<User> findByRolesContaining(Role role);

    // used for security
    Optional<User> findByEmail(String email);

    List<User> findByIsActiveFalse();

    List<User> findByIsActiveTrue();
}