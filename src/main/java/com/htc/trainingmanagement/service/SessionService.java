package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.SessionRequestDto;
import com.htc.trainingmanagement.dto.response.SessionResponseDto;

public interface SessionService {

    SessionResponseDto createSession(SessionRequestDto requestDto);

    SessionResponseDto getSessionById(Long sessionId);

    List<SessionResponseDto> getAllSessions();

    SessionResponseDto updateSession(Long sessionId,SessionRequestDto requestDto);

    boolean deleteSession(Long sessionId);
}