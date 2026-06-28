package com.htc.trainingmanagement.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;
import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.exception.ResourceNotFoundException;
import com.htc.trainingmanagement.mapper.TrainerMapper;
import com.htc.trainingmanagement.repository.TrainerRepository;
import com.htc.trainingmanagement.service.TrainerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    @Override
    public TrainerResponseDto getTrainerById(Long trainerId)
            throws ResourceNotFoundException {

        Trainer trainer = getTrainerEntityById(trainerId);

        return trainerMapper.toResponseDto(trainer);
    }

    @Override
    public List<TrainerResponseDto> getAllTrainers() {

        return trainerRepository.findAll()
                .stream()
                .map(trainerMapper::toResponseDto)
                .toList();
    }

    @Override
    public TrainerResponseDto updateTrainer(
            Long trainerId,
            TrainerRequestDto requestDto)
            throws ResourceNotFoundException {

        Trainer trainer = getTrainerEntityById(trainerId);

        trainerMapper.updateEntity(trainer, requestDto);

        Trainer updatedTrainer = trainerRepository.save(trainer);

        return trainerMapper.toResponseDto(updatedTrainer);
    }

    @Override
    public boolean deleteTrainer(Long trainerId)
            throws ResourceNotFoundException {

        Trainer trainer = getTrainerEntityById(trainerId);

        trainerRepository.delete(trainer);

        return true;
    }

    @Override
    public List<TrainerResponseDto> getTrainersBySpecialization(String specialization) {

        return trainerRepository.findBySpecializationIgnoreCase(specialization)
                .stream()
                .map(trainerMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<TrainerResponseDto> getExperiencedTrainers(Integer yearsOfExperience) {

        return trainerRepository.findByYearsOfExperienceGreaterThanEqual(yearsOfExperience)
                .stream()
                .map(trainerMapper::toResponseDto)
                .toList();
    }

    @Override
    public TrainerResponseDto updateTrainerSpecialization(
            Long trainerId,
            String specialization)
            throws ResourceNotFoundException {

        Trainer trainer = getTrainerEntityById(trainerId);

        // Updates only trainer specialization.
        trainer.setSpecialization(specialization);

        Trainer updatedTrainer = trainerRepository.save(trainer);

        return trainerMapper.toResponseDto(updatedTrainer);
    }

    private Trainer getTrainerEntityById(Long trainerId)
            throws ResourceNotFoundException {

        return trainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainer not found with id: " + trainerId));
    }
}