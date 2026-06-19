package com.htc.trainingmanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.SessionRequestDto;
import com.htc.trainingmanagement.dto.response.SessionResponseDto;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.TrainingBatch;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.repository.SessionRepository;
import com.htc.trainingmanagement.repository.TrainingBatchRepository;
import com.htc.trainingmanagement.service.SessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final TrainingBatchRepository trainingBatchRepository;

    @Override
    public SessionResponseDto createSession(
            SessionRequestDto requestDto) throws ResourceNotFoundException {

        TrainingBatch trainingBatch = trainingBatchRepository
                .findById(requestDto.getTrainingBatchId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training Batch not found with id: "
                                + requestDto.getTrainingBatchId()));

        Session session = new Session();

        session.setSessionTitle(requestDto.getSessionTitle());
        session.setSessionDate(requestDto.getSessionDate());
        session.setStartTime(requestDto.getStartTime());
        session.setEndTime(requestDto.getEndTime());
        session.setTopic(requestDto.getTopic());
        session.setSessionStatus(requestDto.getSessionStatus());
        session.setTrainingBatch(trainingBatch);

        Session savedSession = sessionRepository.save(session);

        return convertToResponseDto(savedSession);
    }

    @Override
    public SessionResponseDto getSessionById(Long sessionId) throws ResourceNotFoundException {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Session not found with id: "
                                + sessionId));

        return convertToResponseDto(session);
    }

    @Override
    public List<SessionResponseDto> getAllSessions() {

        List<Session> sessions = sessionRepository.findAll();

        List<SessionResponseDto> responseDtos = new ArrayList<>();

        for (Session session : sessions) {
            responseDtos.add(convertToResponseDto(session));
        }

        return responseDtos;
    }

    @Override
    public SessionResponseDto updateSession(
            Long sessionId,
            SessionRequestDto requestDto) throws ResourceNotFoundException {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Session not found with id: "
                                + sessionId));

        TrainingBatch trainingBatch = trainingBatchRepository
                .findById(requestDto.getTrainingBatchId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training Batch not found with id: "
                                + requestDto.getTrainingBatchId()));

        session.setSessionTitle(requestDto.getSessionTitle());
        session.setSessionDate(requestDto.getSessionDate());
        session.setStartTime(requestDto.getStartTime());
        session.setEndTime(requestDto.getEndTime());
        session.setTopic(requestDto.getTopic());
        session.setSessionStatus(requestDto.getSessionStatus());
        session.setTrainingBatch(trainingBatch);

        Session updatedSession = sessionRepository.save(session);

        return convertToResponseDto(updatedSession);
    }

    @Override
    public boolean deleteSession(Long sessionId) throws ResourceNotFoundException {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Session not found with id: "
                                + sessionId));

        sessionRepository.delete(session);

        return true;
    }

    private SessionResponseDto convertToResponseDto(
            Session session) {

        return new SessionResponseDto(
                session.getSessionId(),
                session.getSessionTitle(),
                session.getSessionDate(),
                session.getStartTime(),
                session.getEndTime(),
                session.getTopic(),
                session.getSessionStatus(),
                session.getTrainingBatch().getTrainingbatchId(),
                session.getCreatedAt(),
                session.getUpdatedAt());
    }
}