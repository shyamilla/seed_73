package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;
import com.htc.trainingmanagement.enums.TraineeStatus;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface TraineeService {

    TraineeResponseDto getTraineeById(Long traineeId)
            throws ResourceNotFoundException;

    List<TraineeResponseDto> getAllTrainees();

    TraineeResponseDto updateTrainee(
            Long traineeId,
            TraineeRequestDto requestDto)
            throws ResourceNotFoundException;

    boolean deleteTrainee(Long traineeId)
            throws ResourceNotFoundException;

    List<TraineeResponseDto> getTraineesByDepartment(String department);

    List<TraineeResponseDto> getTraineesByStatus(TraineeStatus status);

    TraineeResponseDto updateTraineeStatus(
            Long traineeId,
            TraineeStatus status)
            throws ResourceNotFoundException;
}
