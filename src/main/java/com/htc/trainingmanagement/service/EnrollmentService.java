package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.EnrollmentRequestDto;
import com.htc.trainingmanagement.dto.response.EnrollmentResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface EnrollmentService {

    EnrollmentResponseDto createEnrollment(EnrollmentRequestDto requestDto) throws ResourceNotFoundException;

    EnrollmentResponseDto getEnrollmentById(Long enrollmentId) throws ResourceNotFoundException;

    List<EnrollmentResponseDto> getAllEnrollments();

    EnrollmentResponseDto updateEnrollment(Long enrollmentId, EnrollmentRequestDto requestDto) throws ResourceNotFoundException;

    boolean deleteEnrollment(Long enrollmentId) throws ResourceNotFoundException;

}
