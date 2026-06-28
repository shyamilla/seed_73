package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.UserRequestDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.enums.RoleName;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface UserService {

        UserResponseDto createUser(UserRequestDto requestDto)
                        throws DuplicateResourceException, ResourceNotFoundException;

        UserResponseDto updateUser(Long userId, UserRequestDto requestDto)
                        throws ResourceNotFoundException, DuplicateResourceException;

        boolean deleteUser(Long userId) throws ResourceNotFoundException;

        UserResponseDto getUserById(Long userId) throws ResourceNotFoundException;

        List<UserResponseDto> getAllUsers();

        // others

        List<UserResponseDto> getUsersByRole(RoleName roleName)
                        throws ResourceNotFoundException;

        UserResponseDto assignRole(Long userId, RoleName roleName)
                        throws ResourceNotFoundException, DuplicateResourceException;

        UserResponseDto removeRole(Long userId, RoleName roleName)
                        throws ResourceNotFoundException;

        UserResponseDto changePassword(Long userId, String newPassword)
                        throws ResourceNotFoundException;
}
