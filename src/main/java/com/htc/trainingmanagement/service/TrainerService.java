package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;

public interface TrainerService {

    TrainerResponseDto createTrainer(TrainerRequestDto requestDto);

    TrainerResponseDto getTrainerById(Long trainerId);

    List<TrainerResponseDto> getAllTrainers();

    TrainerResponseDto updateTrainer(Long trainerId,TrainerRequestDto requestDto);

    boolean deleteTrainer(Long trainerId);
}
