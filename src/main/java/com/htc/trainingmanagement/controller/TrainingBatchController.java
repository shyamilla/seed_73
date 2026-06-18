package com.htc.trainingmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.htc.trainingmanagement.dto.request.TrainingBatchRequestDto;
import com.htc.trainingmanagement.dto.response.TrainingBatchResponseDto;
import com.htc.trainingmanagement.serviceimpl.TrainingBatchServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trainingbatches")
@RequiredArgsConstructor
public class TrainingBatchController {

    private final TrainingBatchServiceImpl trainingBatchServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<TrainingBatchResponseDto> createTrainingBatch(TrainingBatchRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingBatchServiceImpl.createTrainingBatch(requestDto));
    }

    @PutMapping("/update/{trainingBatchId}")
    public ResponseEntity<TrainingBatchResponseDto> updateTrainingBatch(@PathVariable Long trainingBatchId,
            @Valid @RequestBody TrainingBatchRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(trainingBatchServiceImpl.updateTrainingBatch(trainingBatchId, requestDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrainingBatchResponseDto>> getAllTrainingBatches() {
        return ResponseEntity.status(HttpStatus.OK).body(trainingBatchServiceImpl.getAllTrainingBatch());
    }

    @GetMapping("/find/{trainingBatchId}")
    public ResponseEntity<TrainingBatchResponseDto> findById(@PathVariable Long trainingBatchId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(trainingBatchServiceImpl.getTrainingBatchById(trainingBatchId));
    }

    @DeleteMapping("/delete/{trainingBatchId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long trainingBatchId) {
        return ResponseEntity.status(HttpStatus.OK).body(trainingBatchServiceImpl.deleteTrainingBatch(trainingBatchId));
    }
}
