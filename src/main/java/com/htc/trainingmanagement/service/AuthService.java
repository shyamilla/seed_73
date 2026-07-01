package com.htc.trainingmanagement.service;

import com.htc.trainingmanagement.dto.request.SetupAdminRequestDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface AuthService {

    UserResponseDto setupFirstAdmin(SetupAdminRequestDto requestDto) throws DuplicateResourceException, ResourceNotFoundException;

    void setupRoles();
}