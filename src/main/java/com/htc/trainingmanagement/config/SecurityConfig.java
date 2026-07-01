package com.htc.trainingmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.htc.trainingmanagement.serviceimpl.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final CustomUserDetailsService customUserDetailsService;

        // For password hashing with Bcrypt
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Configures a DAO-based authentication provider that authenticates users
        // by loading their details from the database using the
        // CustomUserDetailsService.
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {

                // Creates a DaoAuthenticationProvider with the custom UserDetailsService.
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);

                // Sets BCrypt as the password encoder for password verification.
                provider.setPasswordEncoder(passwordEncoder());

                // Returns the configured authentication provider.
                return provider;
        }

        // Receives the authentication request and delegates it to the appropriate
        // provider.
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http.csrf(csrf -> csrf.disable())
                                .authenticationProvider(authenticationProvider())
                                .authorizeHttpRequests(auth -> auth

                                                // Public APIs
                                                .requestMatchers(
                                                                "/auth/login",
                                                                "/auth/setup-admin",
                                                                "/auth/refresh-token",
                                                                "/auth/setup-roles",
                                                                "/auth/logout",
                                                                "/error")
                                                .permitAll()

                                                // Specific trainer endpoint first
                                                .requestMatchers("/trainers/my-trainees").hasAnyRole("ADMIN", "TRAINER")

                                                // Admin only
                                                .requestMatchers("/users/**").hasRole("ADMIN")
                                                .requestMatchers("/courses/**").hasRole("ADMIN")
                                                .requestMatchers("/batches/**").hasRole("ADMIN")
                                                .requestMatchers("/enrollments/**").hasRole("ADMIN")
                                                .requestMatchers("/courses/admin/**").hasRole("ADMIN")

                                                // Admin + Trainer
                                                .requestMatchers("/sessions/**").hasAnyRole("ADMIN", "TRAINER")
                                                .requestMatchers("/attendance/**").hasAnyRole("ADMIN", "TRAINER")

                                                // Trainer profile APIs
                                                .requestMatchers("/trainers/**").hasRole("ADMIN")

                                                // Trainee profile APIs
                                                .requestMatchers("/trainees/me").hasRole("TRAINEE")
                                                .requestMatchers("/trainees/**").hasRole("ADMIN")

                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .formLogin(form -> form.disable())
                                .httpBasic(httpBasic -> httpBasic.disable());

                return http.build();
        }

}
