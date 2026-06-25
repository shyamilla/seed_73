package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.enums.AttendanceStatus;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface AttendanceService {

    AttendanceResponseDto createAttendance(AttendanceRequestDto requestDto) throws ResourceNotFoundException;

    AttendanceResponseDto getAttendanceById(Long attendanceId) throws ResourceNotFoundException;

    List<AttendanceResponseDto> getAllAttendances();

    AttendanceResponseDto updateAttendance(Long attendanceId, AttendanceRequestDto requestDto)
            throws DuplicateResourceException, ResourceNotFoundException;

    boolean deleteAttendance(Long attendanceId) throws ResourceNotFoundException;

    // extra
    List<AttendanceResponseDto> getAttendanceByTrainee(Long traineeId)
            throws ResourceNotFoundException;

    AttendanceResponseDto updateAttendanceStatus(
            Long attendanceId,
            AttendanceStatus attendanceStatus)
            throws ResourceNotFoundException;
}