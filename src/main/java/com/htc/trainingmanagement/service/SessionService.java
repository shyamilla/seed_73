package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.SessionRequestDto;
import com.htc.trainingmanagement.dto.response.SessionResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface SessionService {

    SessionResponseDto createSession(SessionRequestDto requestDto) throws ResourceNotFoundException;

    SessionResponseDto getSessionById(Long sessionId) throws ResourceNotFoundException;

    List<SessionResponseDto> getAllSessions();

    SessionResponseDto updateSession(Long sessionId,SessionRequestDto requestDto) throws ResourceNotFoundException;

    boolean deleteSession(Long sessionId) throws ResourceNotFoundException;
}