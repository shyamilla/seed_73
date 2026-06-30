package com.htc.trainingmanagement.service;

import java.util.List;

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;
import com.htc.trainingmanagement.dto.response.TrainerTraineeResponseDto;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;

public interface TrainerService {

        TrainerResponseDto getTrainerById(Long trainerId)
                        throws ResourceNotFoundException;

        List<TrainerResponseDto> getAllTrainers();

        TrainerResponseDto updateTrainer(Long trainerId, TrainerRequestDto requestDto)
                        throws ResourceNotFoundException;

        boolean deleteTrainer(Long trainerId)
                        throws ResourceNotFoundException;

        List<TrainerResponseDto> getTrainersBySpecialization(String specialization);

        List<TrainerResponseDto> getExperiencedTrainers(Integer yearsOfExperience);

        TrainerResponseDto updateTrainerSpecialization(
                        Long trainerId,
                        String specialization)
                        throws ResourceNotFoundException;

        List<TrainerTraineeResponseDto> getMyTrainees() throws ResourceNotFoundException;
}