package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;

public interface AttendanceService {

    AttendanceResponseDto createAttendance(AttendanceRequestDto requestDto);

    AttendanceResponseDto getAttendanceById(Long attendanceId);

    List<AttendanceResponseDto> getAllAttendances();

    AttendanceResponseDto updateAttendance(Long attendanceId,AttendanceRequestDto requestDto);

    boolean deleteAttendance(Long attendanceId);
}