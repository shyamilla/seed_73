package com.htc.trainingmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.htc.trainingmanagement.dto.request.LoginRequestDto;
import com.htc.trainingmanagement.dto.request.RefreshTokenRequestDto;
import com.htc.trainingmanagement.dto.request.SetupAdminRequestDto;
import com.htc.trainingmanagement.dto.response.LoginResponseDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.exception.UserException;
import com.htc.trainingmanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @PostMapping("/setup-admin")
        public ResponseEntity<UserResponseDto> setupAdmin(
                        @Valid @RequestBody SetupAdminRequestDto requestDto)
                        throws DuplicateResourceException, ResourceNotFoundException {

                UserResponseDto response = authService.setupFirstAdmin(requestDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDto> login(
                        @Valid @RequestBody LoginRequestDto requestDto)
                        throws ResourceNotFoundException, UserException {

                LoginResponseDto response = authService.login(requestDto);
                return ResponseEntity.ok(response);
        }

        @PostMapping("/logout")
        public ResponseEntity<String> logout(
                        @Valid @RequestBody RefreshTokenRequestDto requestDto) {

                authService.logout(requestDto);
                return ResponseEntity.ok("Logout Successful");
        }

        @PostMapping("/refresh-token")
        public ResponseEntity<LoginResponseDto> refreshToken(
                        @Valid @RequestBody RefreshTokenRequestDto requestDto)
                        throws ResourceNotFoundException {

                LoginResponseDto response = authService.refreshToken(requestDto);
                return ResponseEntity.ok(response);
        }

        @PostMapping("/setup-roles")
        public ResponseEntity<String> setupRoles() {
                authService.setupRoles();
                return ResponseEntity.ok("Roles Created in the DB");
        }
}