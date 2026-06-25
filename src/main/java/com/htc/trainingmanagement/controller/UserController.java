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
import org.springframework.web.bind.annotation.RestController;

import com.htc.trainingmanagement.dto.request.UserRequestDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.enums.RoleName;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.serviceimpl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto requestDto)
            throws DuplicateResourceException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceImpl.createUser(requestDto));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId,@Valid @RequestBody UserRequestDto requestDto)
            throws ResourceNotFoundException, DuplicateResourceException {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.updateUser(userId, requestDto));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.deleteUser(userId));
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.getUserById(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.getAllUsers());
    }

    @GetMapping("/roles/{roleName}")
    public ResponseEntity<List<UserResponseDto>> getUsersByRoles(@PathVariable RoleName roleName)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.getUsersByRole(roleName));
    }

    @PatchMapping("/{userId}/assign")
    public ResponseEntity<UserResponseDto> assignRole(@PathVariable Long userId, @RequestBody RoleName roleName)
            throws ResourceNotFoundException, DuplicateResourceException {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.assignRole(userId, roleName));

    }

    @PatchMapping("/{userId}/remove")
    public ResponseEntity<UserResponseDto> removeRole(@PathVariable Long userId, @RequestBody RoleName roleName)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImpl.removeRole(userId, roleName));
    }
}
