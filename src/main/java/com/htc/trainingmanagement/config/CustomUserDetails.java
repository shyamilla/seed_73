package com.htc.trainingmanagement.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.htc.trainingmanagement.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert my application's roles into Spring Security authorities so that Spring can decide what the user is allowed to access.
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
        return user.getEmail(); // login using email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
        // or return user.getIsActive();
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public String getName() {
        return user.getUserName();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public User getUser() {
        return user;
    }
}
