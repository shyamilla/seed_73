package com.htc.trainingmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htc.trainingmanagement.dto.request.EnrollmentRequestDto;
import com.htc.trainingmanagement.dto.response.EnrollmentResponseDto;
import com.htc.trainingmanagement.enums.EnrollmentStatus;
import com.htc.trainingmanagement.exception.CapacityExceededException;
import com.htc.trainingmanagement.exception.EnrollmentException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.service.EnrollmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

        private final EnrollmentService enrollmentService;

        @PostMapping("/add")
        public ResponseEntity<EnrollmentResponseDto> addEnrollment(
                        @Valid @RequestBody EnrollmentRequestDto requestDto)
                        throws ResourceNotFoundException, EnrollmentException, CapacityExceededException {
                return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.createEnrollment(requestDto));
        }

        @PutMapping("/update/{enrollmentId}")
        public ResponseEntity<EnrollmentResponseDto> updateEnrollment(
                        @PathVariable Long enrollmentId,
                        @Valid @RequestBody EnrollmentRequestDto requestDto)
                        throws ResourceNotFoundException, EnrollmentException, CapacityExceededException {
                return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.updateEnrollment(enrollmentId, requestDto));
        }

        @GetMapping("/find/{enrollmentId}")
        public ResponseEntity<EnrollmentResponseDto> getEnrollmentById(
                        @PathVariable Long enrollmentId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.getEnrollmentById(enrollmentId));
        }

        @GetMapping("/all")
        public ResponseEntity<List<EnrollmentResponseDto>> getAllEnrollments() {
                return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.getAllEnrollments());
        }

        @DeleteMapping("/delete/{enrollmentId}")
        public ResponseEntity<Boolean> deleteEnrollment(
                        @PathVariable Long enrollmentId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.deleteEnrollment(enrollmentId));
        }

        @GetMapping("/trainee/{traineeId}")
        public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByTrainee(
                        @PathVariable Long traineeId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.getEnrollmentsByTrainee(traineeId));
        }

        @GetMapping("/batch/{trainingBatchId}")
        public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByBatch(
                        @PathVariable Long trainingBatchId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.getEnrollmentsByBatch(trainingBatchId));
                                
        }

        @PatchMapping("/{enrollmentId}/status")
        public ResponseEntity<EnrollmentResponseDto> updateCompletionStatus(
                        @PathVariable Long enrollmentId,
                        @RequestParam EnrollmentStatus completionStatus)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.updateCompletionStatus(enrollmentId, completionStatus));
        }
}