package com.htc.trainingmanagement.service;

import com.htc.trainingmanagement.entity.RefreshToken;
import com.htc.trainingmanagement.entity.User;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token) throws ResourceNotFoundException;

    void deleteRefreshToken(String refreshToken);
}