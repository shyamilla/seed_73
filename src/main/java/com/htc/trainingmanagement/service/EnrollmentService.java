package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.EnrollmentRequestDto;
import com.htc.trainingmanagement.dto.response.EnrollmentResponseDto;

public interface EnrollmentService {

    EnrollmentResponseDto createEnrollment(EnrollmentRequestDto requestDto);

    EnrollmentResponseDto getEnrollmentById(Long enrollmentId);

    List<EnrollmentResponseDto> getAllEnrollments();

    EnrollmentResponseDto updateEnrollment(Long enrollmentId, EnrollmentRequestDto requestDto);

    boolean deleteEnrollment(Long enrollmentId);

}
