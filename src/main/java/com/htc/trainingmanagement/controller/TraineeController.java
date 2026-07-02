package com.htc.trainingmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;
import com.htc.trainingmanagement.enums.TraineeStatus;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.service.TraineeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trainees")
@RequiredArgsConstructor
public class TraineeController {

        private final TraineeService traineeService;

        @PutMapping("/update/{traineeId}")
        public ResponseEntity<TraineeResponseDto> updateTrainee(
                        @PathVariable Long traineeId,
                        @Valid @RequestBody TraineeRequestDto requestDto)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(traineeService.updateTrainee(traineeId, requestDto));
        }

        @GetMapping("/find/{traineeId}")
        public ResponseEntity<TraineeResponseDto> getTraineeById(
                        @PathVariable Long traineeId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body( traineeService.getTraineeById(traineeId));
        }

        @GetMapping("/all")
        public ResponseEntity<List<TraineeResponseDto>> getAllTrainees() {
                return ResponseEntity.status(HttpStatus.OK).body(traineeService.getAllTrainees());
        }

        @DeleteMapping("/delete/{traineeId}")
        public ResponseEntity<Boolean> deleteTrainee(
                        @PathVariable Long traineeId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(traineeService.deleteTrainee(traineeId));
        }

        @GetMapping("/department/{department}")
        public ResponseEntity<List<TraineeResponseDto>> getTraineesByDepartment(
                        @PathVariable String department) {
                return ResponseEntity.status(HttpStatus.OK).body(traineeService.getTraineesByDepartment(department));
                                
        }

        @GetMapping("/status/{status}")
        public ResponseEntity<List<TraineeResponseDto>> getTraineesByStatus(
                        @PathVariable TraineeStatus status) {

                return ResponseEntity.ok(
                                traineeService.getTraineesByStatus(status));
        }

        @PatchMapping("/{traineeId}/status")
        public ResponseEntity<TraineeResponseDto> updateTraineeStatus(
                        @PathVariable Long traineeId,
                        @RequestParam TraineeStatus status)
                        throws ResourceNotFoundException {

                return ResponseEntity.ok(
                                traineeService.updateTraineeStatus(traineeId, status));
        }

        @GetMapping("/me")
        public ResponseEntity<TraineeResponseDto> getMyProfile() throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(traineeService.getMyProfile());
        }

        @GetMapping("/inactive")
        public ResponseEntity<List<TraineeResponseDto>> getInactiveTrainees() {
                return ResponseEntity.status(HttpStatus.OK).body(traineeService.getInactiveTrainees());
        }

        @GetMapping("/active")
        public ResponseEntity<List<TraineeResponseDto>> getActiveTrainees() {
                return ResponseEntity.status(HttpStatus.OK).body(traineeService.getActiveTrainees());
        }
}