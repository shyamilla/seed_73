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

import com.htc.trainingmanagement.dto.request.TrainingBatchRequestDto;
import com.htc.trainingmanagement.dto.response.TrainingBatchResponseDto;
import com.htc.trainingmanagement.enums.BatchStatus;
import com.htc.trainingmanagement.exception.BatchException;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.exception.TrainerException;
import com.htc.trainingmanagement.service.TrainingBatchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trainingbatches")
@RequiredArgsConstructor
public class TrainingBatchController {

        private final TrainingBatchService trainingBatchService;

        @PostMapping("/add")
        public ResponseEntity<TrainingBatchResponseDto> createTrainingBatch(
                        @Valid @RequestBody TrainingBatchRequestDto requestDto)
                        throws ResourceNotFoundException, TrainerException, BatchException {
                return ResponseEntity.status(HttpStatus.CREATED).body(trainingBatchService.createTrainingBatch(requestDto));
        }

        @PutMapping("/update/{trainingBatchId}")
        public ResponseEntity<TrainingBatchResponseDto> updateTrainingBatch(
                        @PathVariable Long trainingBatchId,
                        @Valid @RequestBody TrainingBatchRequestDto requestDto)
                        throws ResourceNotFoundException, BatchException {
                return ResponseEntity.status(HttpStatus.OK).body(trainingBatchService.updateTrainingBatch(trainingBatchId, requestDto));
        }

        @GetMapping("/all")
        public ResponseEntity<List<TrainingBatchResponseDto>> getAllTrainingBatches() {
                return ResponseEntity.status(HttpStatus.OK).body(trainingBatchService.getAllTrainingBatch());
        }

        @GetMapping("/find/{trainingBatchId}")
        public ResponseEntity<TrainingBatchResponseDto> getTrainingBatchById(
                        @PathVariable Long trainingBatchId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainingBatchService.getTrainingBatchById(trainingBatchId));
        }

        @DeleteMapping("/delete/{trainingBatchId}")
        public ResponseEntity<Boolean> deleteTrainingBatch(
                        @PathVariable Long trainingBatchId)
                        throws ResourceNotFoundException, BatchException {
                return ResponseEntity.status(HttpStatus.OK).body(trainingBatchService.deleteTrainingBatch(trainingBatchId));
        }

        @GetMapping("/course/{courseId}")
        public ResponseEntity<List<TrainingBatchResponseDto>> getBatchesByCourse(
                        @PathVariable Long courseId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainingBatchService.getBatchesByCourse(courseId));
        }

        @GetMapping("/trainer/{trainerId}")
        public ResponseEntity<List<TrainingBatchResponseDto>> getBatchesByTrainer(
                        @PathVariable Long trainerId)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainingBatchService.getBatchesByTrainer(trainerId));
        }

        @PatchMapping("/{trainingBatchId}/status")
        public ResponseEntity<TrainingBatchResponseDto> updateBatchStatus(
                        @PathVariable Long trainingBatchId,
                        @RequestParam BatchStatus status)
                        throws ResourceNotFoundException {
                return ResponseEntity.status(HttpStatus.OK).body(trainingBatchService.updateBatchStatus(trainingBatchId, status));
        }
}