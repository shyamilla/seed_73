package com.htc.trainingmanagement.service;

import java.time.LocalDate;
import java.util.List;

import com.htc.trainingmanagement.dto.request.AttendanceRequestDto;
import com.htc.trainingmanagement.dto.response.AttendanceResponseDto;
import com.htc.trainingmanagement.enums.AttendanceStatus;
import com.htc.trainingmanagement.exception.AttendanceException;
import com.htc.trainingmanagement.exception.DuplicateResourceException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface AttendanceService {

        AttendanceResponseDto createAttendance(AttendanceRequestDto requestDto)
                        throws ResourceNotFoundException, DuplicateResourceException, AttendanceException;

        AttendanceResponseDto getAttendanceById(Long attendanceId)
                        throws ResourceNotFoundException;

        List<AttendanceResponseDto> getAllAttendances();

        AttendanceResponseDto updateAttendance(
                        Long attendanceId,
                        AttendanceRequestDto requestDto)
                        throws DuplicateResourceException, ResourceNotFoundException, AttendanceException;

        boolean deleteAttendance(Long attendanceId)
                        throws ResourceNotFoundException;

        // other
        List<AttendanceResponseDto> getAttendanceByTrainee(Long traineeId)
                        throws ResourceNotFoundException;

        List<AttendanceResponseDto> getAttendanceBySession(Long sessionId)
                        throws ResourceNotFoundException;

        List<AttendanceResponseDto> getAttendanceByDate(LocalDate attendanceDate);

        AttendanceResponseDto updateAttendanceStatus(
                        Long attendanceId,
                        AttendanceStatus attendanceStatus)
                        throws ResourceNotFoundException;
}