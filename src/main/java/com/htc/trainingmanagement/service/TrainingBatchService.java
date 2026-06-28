package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.TrainingBatchRequestDto;
import com.htc.trainingmanagement.dto.response.TrainingBatchResponseDto;
import com.htc.trainingmanagement.enums.BatchStatus;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface TrainingBatchService {

    TrainingBatchResponseDto createTrainingBatch(TrainingBatchRequestDto requestDto)
            throws ResourceNotFoundException;

    TrainingBatchResponseDto getTrainingBatchById(Long trainingBatchId)
            throws ResourceNotFoundException;

    List<TrainingBatchResponseDto> getAllTrainingBatch();

    TrainingBatchResponseDto updateTrainingBatch(
            Long trainingBatchId,
            TrainingBatchRequestDto requestDto)
            throws ResourceNotFoundException;

    boolean deleteTrainingBatch(Long trainingBatchId)
            throws ResourceNotFoundException;

    List<TrainingBatchResponseDto> getBatchesByCourse(Long courseId)
            throws ResourceNotFoundException;

    List<TrainingBatchResponseDto> getBatchesByTrainer(Long trainerId)
            throws ResourceNotFoundException;

    TrainingBatchResponseDto updateBatchStatus(Long trainingBatchId, BatchStatus status)
            throws ResourceNotFoundException;
}