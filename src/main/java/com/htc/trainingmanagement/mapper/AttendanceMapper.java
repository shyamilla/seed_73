package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.entity.Attendance;

@Component
public class AttendanceMapper {

    public Attendance toEntity(AttendanceRequestDto requestDto) {

        Attendance attendance = new Attendance();

        attendance.setAttendanceDate(requestDto.getAttendanceDate());
        attendance.setAttendanceStatus(requestDto.getAttendanceStatus());
        attendance.setRemarks(requestDto.getRemarks());

        return attendance;
    }

    public void updateEntity(Attendance attendance, AttendanceRequestDto requestDto) {

        attendance.setAttendanceDate(requestDto.getAttendanceDate());
        attendance.setAttendanceStatus(requestDto.getAttendanceStatus());
        attendance.setRemarks(requestDto.getRemarks());
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
                attendance.getUpdatedAt()
        );
    }
}