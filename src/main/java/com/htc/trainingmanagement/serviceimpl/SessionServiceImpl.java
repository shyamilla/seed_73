package com.htc.trainingmanagement.serviceimpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.SessionRequestDto;
import com.htc.trainingmanagement.dto.response.SessionResponseDto;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.TrainingBatch;
import com.htc.trainingmanagement.enums.SessionStatus;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.SessionMapper;
import com.htc.trainingmanagement.repository.SessionRepository;
import com.htc.trainingmanagement.repository.TrainingBatchRepository;
import com.htc.trainingmanagement.service.SessionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

        private final SessionRepository sessionRepository;
        private final TrainingBatchRepository trainingBatchRepository;
        private final SessionMapper sessionMapper;

        // Creates a new session for a training batch.
        @Override
        public SessionResponseDto createSession(SessionRequestDto requestDto)
                        throws ResourceNotFoundException {

                // Fetches the training batch for the session.
                TrainingBatch trainingBatch = getTrainingBatchEntityById(
                                requestDto.getTrainingBatchId());

                // Validates that session date is within batch start and end date.
                validateSessionDate(requestDto, trainingBatch);

                validateSessionTime(requestDto);

                // Converts request DTO to Session entity.
                Session session = sessionMapper.toEntity(requestDto, trainingBatch);

                // Saves the session.
                Session savedSession = sessionRepository.save(session);

                // Returns saved session response.
                return sessionMapper.toResponseDto(savedSession);
        }

        // Retrieves a session by ID.
        @Override
        public SessionResponseDto getSessionById(Long sessionId)
                        throws ResourceNotFoundException {

                // Fetches the session by ID.
                Session session = getSessionEntityById(sessionId);

                // Returns session response.
                return sessionMapper.toResponseDto(session);
        }

        // Retrieves all sessions.
        @Override
        public List<SessionResponseDto> getAllSessions() {

                return sessionRepository.findAll()
                                .stream()
                                .map(sessionMapper::toResponseDto)
                                .toList();
        }

        // Updates an existing session.
        @Override
        public SessionResponseDto updateSession(
                        Long sessionId,
                        SessionRequestDto requestDto)
                        throws ResourceNotFoundException {

                // Fetches the existing session.
                Session session = getSessionEntityById(sessionId);

                // Fetches the updated training batch.
                TrainingBatch trainingBatch = getTrainingBatchEntityById(
                                requestDto.getTrainingBatchId());

                // Validates that session date is within batch start and end date.
                validateSessionDate(requestDto, trainingBatch);
                validateSessionTime(requestDto);

                // Updates session fields from request DTO.
                sessionMapper.updateEntity(session, requestDto);

                // Updates the associated training batch.
                session.setTrainingBatch(trainingBatch);

                // Saves updated session.
                Session updatedSession = sessionRepository.save(session);

                // Returns updated session response.
                return sessionMapper.toResponseDto(updatedSession);
        }

        // Deletes a session by ID.
        @Override
        public boolean deleteSession(Long sessionId)
                        throws ResourceNotFoundException {

                // Fetches the session to ensure it exists.
                Session session = getSessionEntityById(sessionId);

                // Soft deletes the session.
                sessionRepository.delete(session);

                return true;
        }

        // Retrieves all sessions for a specific training batch.
        @Override
        public List<SessionResponseDto> getSessionsByBatch(Long trainingBatchId)
                        throws ResourceNotFoundException {

                // Validates that the training batch exists.
                getTrainingBatchEntityById(trainingBatchId);

                return sessionRepository.findByTrainingBatchTrainingbatchId(trainingBatchId)
                                .stream()
                                .map(sessionMapper::toResponseDto)
                                .toList();
        }

        // Retrieves sessions by session date.
        @Override
        public List<SessionResponseDto> getSessionsByDate(LocalDate sessionDate) {

                return sessionRepository.findBySessionDate(sessionDate)
                                .stream()
                                .map(sessionMapper::toResponseDto)
                                .toList();
        }

        // Updates only the session status.
        @Override
        public SessionResponseDto updateSessionStatus(
                        Long sessionId,
                        SessionStatus status)
                        throws ResourceNotFoundException {

                // Fetches the session by ID.
                Session session = getSessionEntityById(sessionId);

                // Updates only the session status.
                session.setSessionStatus(status);

                // Saves updated session.
                Session updatedSession = sessionRepository.save(session);

                // Returns updated session response.
                return sessionMapper.toResponseDto(updatedSession);
        }

        // Helper method to fetch a Session by ID or throw exception if not found.
        private Session getSessionEntityById(Long sessionId)
                        throws ResourceNotFoundException {

                return sessionRepository.findById(sessionId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Session not found with id: " + sessionId));
        }

        // Helper method to fetch a TrainingBatch by ID or throw exception if not found.
        private TrainingBatch getTrainingBatchEntityById(Long trainingBatchId)
                        throws ResourceNotFoundException {

                return trainingBatchRepository.findById(trainingBatchId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: " + trainingBatchId));
        }

        // Helper method to validate that session date is within batch duration.
        private void validateSessionDate(
                        SessionRequestDto requestDto,
                        TrainingBatch trainingBatch) {

                if (requestDto.getSessionDate().isBefore(trainingBatch.getStartDate())
                                || requestDto.getSessionDate().isAfter(trainingBatch.getEndDate())) {

                        throw new IllegalArgumentException(
                                        "Session date must be within the training batch duration.");
                }
        }

        // Helper method to validate that session end time is after start time.
        private void validateSessionTime(SessionRequestDto requestDto) {

                if (!requestDto.getEndTime().isAfter(requestDto.getStartTime())) {
                        throw new IllegalArgumentException(
                                        "Session end time must be after start time.");
                }
        }
}