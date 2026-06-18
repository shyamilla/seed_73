package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;

public interface TraineeService {

    TraineeResponseDto createTrainee(TraineeRequestDto requestDto);

    TraineeResponseDto getTraineeById(Long traineeId);

    List<TraineeResponseDto> getAllTrainees();

    TraineeResponseDto updateTrainee(Long traineeId, TraineeRequestDto requestDto);

    boolean deleteTrainee(Long traineeId);

}
