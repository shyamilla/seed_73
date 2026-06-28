package com.htc.trainingmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // .csrf(Customizer.withDefaults()) // Enables CSRF protection.
                .csrf(csrf -> csrf.disable()) // disables CSRF protection.

                .sessionManagement(session -> session // Creates a session only when required.
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .formLogin(Customizer.withDefaults()) // Enables browser-based login page.

                .httpBasic(Customizer.withDefaults()); // Enables browser-based login page.

        return http.build();
    }
}
