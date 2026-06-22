package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.entity.Attendance;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.Trainee;

@Component
public class AttendanceMapper {

    public Attendance toEntity(AttendanceRequestDto requestDto) {

        Attendance attendance = new Attendance();
        Trainee trainee = new Trainee();
        Session session = new Session();

        attendance.setAttendanceDate(requestDto.getAttendanceDate());
        attendance.setAttendanceStatus(requestDto.getAttendanceStatus());
        attendance.setRemarks(requestDto.getRemarks());

        trainee.setTraineeId(requestDto.getTraineeId());

        session.setSessionId(requestDto.getSessionId());

        attendance.setTrainee(trainee);
        attendance.setSession(session);

        return attendance;
    }

    public AttendanceResponseDto toResponseDto(Attendance attendance) {

        return new AttendanceResponseDto(
                attendance.getAttendanceId(),
                attendance.getAttendanceDate(),
                attendance.getAttendanceStatus(),
                attendance.getRemarks(),
                attendance.getTrainee().getTraineeId(),
                attendance.getSession().getSessionId(),
                attendance.getCreatedAt(),
                attendance.getUpdatedAt());
    }

    public void updateEntity(
            Attendance attendance,
            AttendanceRequestDto requestDto) {

        attendance.setAttendanceDate(requestDto.getAttendanceDate());
        attendance.setAttendanceStatus(requestDto.getAttendanceStatus());
        attendance.setRemarks(requestDto.getRemarks());

        Trainee trainee = new Trainee();
        trainee.setTraineeId(requestDto.getTraineeId());

        Session session = new Session();
        session.setSessionId(requestDto.getSessionId());

        attendance.setTrainee(trainee);
        attendance.setSession(session);
    }
}