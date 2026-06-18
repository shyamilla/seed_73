package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.TrainingBatchRequestDto;
import com.htc.trainingmanagement.dto.response.TrainingBatchResponseDto;

public interface TrainingBatchService {

    TrainingBatchResponseDto createTrainingBatch(TrainingBatchRequestDto requestDto);

    TrainingBatchResponseDto getTrainingBatchById(Long trainingBatchId);

    List<TrainingBatchResponseDto> getAllTrainingBatch();

    TrainingBatchResponseDto updateTrainingBatch(Long trainingBatchId, TrainingBatchRequestDto requestDto);

    boolean deleteTrainingBatch(Long trainingBatchId);
}
