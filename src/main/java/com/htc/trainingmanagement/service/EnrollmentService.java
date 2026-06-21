package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.EnrollmentRequestDto;
import com.htc.trainingmanagement.dto.response.EnrollmentResponseDto;
import com.htc.trainingmanagement.exception.CapacityExceededException;
import com.htc.trainingmanagement.exception.EnrollmentException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface EnrollmentService {

    EnrollmentResponseDto createEnrollment(EnrollmentRequestDto requestDto) throws ResourceNotFoundException, EnrollmentException, CapacityExceededException;

    EnrollmentResponseDto getEnrollmentById(Long enrollmentId) throws ResourceNotFoundException;

    List<EnrollmentResponseDto> getAllEnrollments();

    EnrollmentResponseDto updateEnrollment(Long enrollmentId, EnrollmentRequestDto requestDto) throws ResourceNotFoundException, EnrollmentException;

    boolean deleteEnrollment(Long enrollmentId) throws ResourceNotFoundException;

}
