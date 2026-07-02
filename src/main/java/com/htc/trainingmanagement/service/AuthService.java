package com.htc.trainingmanagement.service;

import com.htc.trainingmanagement.dto.request.LoginRequestDto;
import com.htc.trainingmanagement.dto.request.RefreshTokenRequestDto;
import com.htc.trainingmanagement.dto.request.SetupAdminRequestDto;
import com.htc.trainingmanagement.dto.response.LoginResponseDto;
import com.htc.trainingmanagement.dto.response.UserResponseDto;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.exception.UserException;

public interface AuthService {

    UserResponseDto setupFirstAdmin(SetupAdminRequestDto requestDto)
            throws DuplicateResourceException, ResourceNotFoundException;

    LoginResponseDto login(LoginRequestDto requestDto)
            throws ResourceNotFoundException, UserException;

    void logout(RefreshTokenRequestDto requestDto);

    LoginResponseDto refreshToken(RefreshTokenRequestDto requestDto)
            throws ResourceNotFoundException;

    void setupRoles();
}