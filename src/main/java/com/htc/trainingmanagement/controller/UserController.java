package com.htc.trainingmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htc.trainingmanagement.dto.request.ChangePasswordRequestDto;
import com.htc.trainingmanagement.dto.request.UserRequestDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.enums.RoleName;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;

        @PostMapping("/add")
        public ResponseEntity<UserResponseDto> createUser(
                        @Valid @RequestBody UserRequestDto requestDto)
                        throws DuplicateResourceException, ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestDto));
        }

        @PutMapping("/update/{userId}")
        public ResponseEntity<UserResponseDto> updateUser(
                        @PathVariable Long userId,
                        @Valid @RequestBody UserRequestDto requestDto)
                        throws ResourceNotFoundException, DuplicateResourceException {
                return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, requestDto));
        }

        @DeleteMapping("/delete/{userId}")
        public ResponseEntity<Boolean> deleteUser(
                        @PathVariable Long userId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
        }

        @GetMapping("/find/{userId}")
        public ResponseEntity<UserResponseDto> getUserById(
                        @PathVariable Long userId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
        }

        @GetMapping("/all")
        public ResponseEntity<List<UserResponseDto>> getAllUsers() {
                return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        }

        @GetMapping("/role/{roleName}")
        public ResponseEntity<List<UserResponseDto>> getUsersByRole(
                        @PathVariable RoleName roleName)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersByRole(roleName));
        }

        @PatchMapping("/{userId}/assign/{roleName}")
        public ResponseEntity<UserResponseDto> assignRole(
                        @PathVariable Long userId,
                        @PathVariable RoleName roleName)
                        throws ResourceNotFoundException, DuplicateResourceException {
                return ResponseEntity.status(HttpStatus.OK).body(userService.assignRole(userId, roleName));
        }

        @PatchMapping("/{userId}/remove/{roleName}")
        public ResponseEntity<UserResponseDto> removeRole(
                        @PathVariable Long userId,
                        @PathVariable RoleName roleName)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(userService.removeRole(userId, roleName));
        }

        @PatchMapping("/{userId}/password")
        public ResponseEntity<UserResponseDto> changePassword(
                        @PathVariable Long userId,
                        @Valid @RequestBody ChangePasswordRequestDto request)
                        throws Exception {

                return ResponseEntity.ok(userService.changePassword(userId, request));
        }

        @GetMapping("/inactive")
        public ResponseEntity<List<UserResponseDto>> getInactiveUsers() {
                return ResponseEntity.status(HttpStatus.OK).body(userService.getInactiveUsers());
        }

        @GetMapping("/active")
        public ResponseEntity<List<UserResponseDto>> getActiveUsers() {
                return ResponseEntity.status(HttpStatus.OK).body(userService.getActiveUsers());
        }
}