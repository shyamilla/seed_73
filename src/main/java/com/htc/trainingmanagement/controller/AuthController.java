package com.htc.trainingmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.htc.trainingmanagement.config.JwtService;
import com.htc.trainingmanagement.dto.request.LoginRequestDto;
import com.htc.trainingmanagement.dto.request.RefreshTokenRequestDto;
import com.htc.trainingmanagement.dto.request.SetupAdminRequestDto;
import com.htc.trainingmanagement.dto.response.LoginResponseDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.entity.RefreshToken;
import com.htc.trainingmanagement.entity.User;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.repository.UserRepository;
import com.htc.trainingmanagement.service.AuthService;
import com.htc.trainingmanagement.service.RefreshTokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final UserDetailsService userDetailsService;
        private final JwtService jwtService;
        private final AuthService authService;
        private final UserRepository userRepository;
        private final RefreshTokenService refreshTokenService;

        @PostMapping("/setup-admin")
        public ResponseEntity<UserResponseDto> setupAdmin(
                        @Valid @RequestBody SetupAdminRequestDto requestDto)
                        throws DuplicateResourceException, ResourceNotFoundException {

                UserResponseDto response = authService.setupFirstAdmin(requestDto);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDto> login(
                        @Valid @RequestBody LoginRequestDto requestDto) throws ResourceNotFoundException {

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                requestDto.getEmail(),
                                                requestDto.getPassword()));

                UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getEmail());

                User user = userRepository.findByEmail(requestDto.getEmail())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "User not found with email: " + requestDto.getEmail()));

                String accessToken = jwtService.generateToken(userDetails);

                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

                // return ResponseEntity.ok(
                //                 new LoginResponseDto(
                //                                 accessToken,
                //                                 refreshToken.getToken()));
                return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(accessToken, refreshToken.getToken()));
        }

        @PostMapping("/logout")
        public ResponseEntity<String> logout(
                        @RequestBody RefreshTokenRequestDto requestDto) {

                refreshTokenService.deleteRefreshToken(requestDto.getRefreshToken());

                return ResponseEntity.ok("Logged out successfully");
        }

        @PostMapping("/refresh-token")
        public ResponseEntity<LoginResponseDto> refreshToken(
                        @Valid @RequestBody RefreshTokenRequestDto requestDto) throws ResourceNotFoundException {

                RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(
                                requestDto.getRefreshToken());

                User user = refreshToken.getUser();

                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

                String newAccessToken = jwtService.generateToken(userDetails);

                return ResponseEntity.ok(
                                new LoginResponseDto(
                                                newAccessToken,
                                                refreshToken.getToken()));
        }

        @PostMapping("/setup-roles")
        public ResponseEntity<String> setupRoles() {
                authService.setupRoles();
                return ResponseEntity.ok("Roles created successfully");
        }
}