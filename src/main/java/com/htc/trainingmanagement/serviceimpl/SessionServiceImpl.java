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

        @Override
        public SessionResponseDto createSession(SessionRequestDto requestDto)
                        throws ResourceNotFoundException {

                TrainingBatch trainingBatch = getTrainingBatchEntityById(
                                requestDto.getTrainingBatchId());

                Session session = sessionMapper.toEntity(requestDto, trainingBatch);

                Session savedSession = sessionRepository.save(session);

                return sessionMapper.toResponseDto(savedSession);
        }

        @Override
        public SessionResponseDto getSessionById(Long sessionId)
                        throws ResourceNotFoundException {

                Session session = getSessionEntityById(sessionId);

                return sessionMapper.toResponseDto(session);
        }

        @Override
        public List<SessionResponseDto> getAllSessions() {

                return sessionRepository.findAll()
                                .stream()
                                .map(sessionMapper::toResponseDto)
                                .toList();
        }

        @Override
        public SessionResponseDto updateSession(
                        Long sessionId,
                        SessionRequestDto requestDto)
                        throws ResourceNotFoundException {

                Session session = getSessionEntityById(sessionId);

                TrainingBatch trainingBatch = getTrainingBatchEntityById(
                                requestDto.getTrainingBatchId());

                sessionMapper.updateEntity(session, requestDto);

                session.setTrainingBatch(trainingBatch);

                Session updatedSession = sessionRepository.save(session);

                return sessionMapper.toResponseDto(updatedSession);
        }

        @Override
        public boolean deleteSession(Long sessionId)
                        throws ResourceNotFoundException {

                Session session = getSessionEntityById(sessionId);

                sessionRepository.delete(session);

                return true;
        }

        @Override
        public List<SessionResponseDto> getSessionsByBatch(Long trainingBatchId)
                        throws ResourceNotFoundException {

                getTrainingBatchEntityById(trainingBatchId);

                return sessionRepository.findByTrainingBatchTrainingbatchId(trainingBatchId)
                                .stream()
                                .map(sessionMapper::toResponseDto)
                                .toList();
        }

        @Override
        public List<SessionResponseDto> getSessionsByDate(LocalDate sessionDate) {

                return sessionRepository.findBySessionDate(sessionDate)
                                .stream()
                                .map(sessionMapper::toResponseDto)
                                .toList();
        }

        @Override
        public SessionResponseDto updateSessionStatus(
                        Long sessionId,
                        SessionStatus status)
                        throws ResourceNotFoundException {

                Session session = getSessionEntityById(sessionId);

                // Updates only the session status.
                session.setSessionStatus(status);

                Session updatedSession = sessionRepository.save(session);

                return sessionMapper.toResponseDto(updatedSession);
        }

        private Session getSessionEntityById(Long sessionId)
                        throws ResourceNotFoundException {

                return sessionRepository.findById(sessionId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Session not found with id: " + sessionId));
        }

        private TrainingBatch getTrainingBatchEntityById(Long trainingBatchId)
                        throws ResourceNotFoundException {

                return trainingBatchRepository.findById(trainingBatchId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Training Batch not found with id: " + trainingBatchId));
        }
}