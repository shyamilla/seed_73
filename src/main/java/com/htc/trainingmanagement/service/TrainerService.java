package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface TrainerService {

    TrainerResponseDto createTrainer(TrainerRequestDto requestDto);

    TrainerResponseDto getTrainerById(Long trainerId) throws ResourceNotFoundException;

    List<TrainerResponseDto> getAllTrainers();

    TrainerResponseDto updateTrainer(Long trainerId,TrainerRequestDto requestDto) throws ResourceNotFoundException;

    boolean deleteTrainer(Long trainerId) throws ResourceNotFoundException;
}
