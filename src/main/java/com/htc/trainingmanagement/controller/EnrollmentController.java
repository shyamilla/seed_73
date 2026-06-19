package com.htc.trainingmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.htc.trainingmanagement.dto.request.EnrollmentRequestDto;
import com.htc.trainingmanagement.dto.response.EnrollmentResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.serviceimpl.EnrollmentServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentServiceImpl enrollmentServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<EnrollmentResponseDto> addEnrollment(@Valid @RequestBody EnrollmentRequestDto requestDto)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentServiceImpl.createEnrollment(requestDto));
    }

    @PutMapping("/update/{enrollmentId}")
    public ResponseEntity<EnrollmentResponseDto> updateEnrollment(@PathVariable Long enrollmentId,
            @Valid @RequestBody EnrollmentRequestDto requestDto) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(enrollmentServiceImpl.updateEnrollment(enrollmentId, requestDto));
    }

    @GetMapping("/find/{enrollmentId}")
    public ResponseEntity<EnrollmentResponseDto> findById(@PathVariable Long enrollmentId)
            throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentServiceImpl.getEnrollmentById(enrollmentId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EnrollmentResponseDto>> getAllEnrollments() {
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentServiceImpl.getAllEnrollments());
    }

    @DeleteMapping("/delete/{enrollmentId}")
    public ResponseEntity<Boolean> deleteEnrollment(@PathVariable Long enrollmentId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(enrollmentServiceImpl.deleteEnrollment(enrollmentId));
    }
}