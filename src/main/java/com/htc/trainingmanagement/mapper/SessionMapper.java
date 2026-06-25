package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.SessionRequestDto;
import com.htc.trainingmanagement.dto.response.SessionResponseDto;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.TrainingBatch;

@Component
public class SessionMapper {

    public Session toEntity(
            SessionRequestDto requestDto,
            TrainingBatch trainingBatch) {

        Session session = new Session();

        session.setSessionTitle(requestDto.getSessionTitle());
        session.setSessionDate(requestDto.getSessionDate());
        session.setStartTime(requestDto.getStartTime());
        session.setEndTime(requestDto.getEndTime());
        session.setTopic(requestDto.getTopic());
        session.setSessionStatus(requestDto.getSessionStatus());
        session.setTrainingBatch(trainingBatch);

        return session;
    }

    public SessionResponseDto toResponseDto(Session session) {

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

    public void updateEntity(
            Session session,
            SessionRequestDto requestDto) {

        session.setSessionTitle(requestDto.getSessionTitle());
        session.setSessionDate(requestDto.getSessionDate());
        session.setStartTime(requestDto.getStartTime());
        session.setEndTime(requestDto.getEndTime());
        session.setTopic(requestDto.getTopic());
        session.setSessionStatus(requestDto.getSessionStatus());
    }
}