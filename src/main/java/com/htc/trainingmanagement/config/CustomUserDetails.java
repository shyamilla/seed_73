package com.htc.trainingmanagement.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.htc.trainingmanagement.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
// Represents the logged-in user in Spring Security format
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert my application's roles into Spring Security authorities so that
        // Spring can decide what the user is allowed to access.
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(
                        "ROLE_" + role.getRoleName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // login using email cause in using email + password
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }
}
