package com.htc.trainingmanagement.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;
import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.repository.TrainerRepository;
import com.htc.trainingmanagement.service.TrainerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    @Override
    public TrainerResponseDto createTrainer(TrainerRequestDto requestDto) {

        Trainer trainer = new Trainer();

        trainer.setName(requestDto.getName());
        trainer.setEmail(requestDto.getEmail());
        trainer.setPhoneNumber(requestDto.getPhoneNumber());
        trainer.setSpecialization(requestDto.getSpecialization());
        trainer.setYearsOfExperience(requestDto.getYearsOfExperience());

        Trainer savedTrainer = trainerRepository.save(trainer);

        return convertToResponseDto(savedTrainer);
    }

    @Override
    public TrainerResponseDto getTrainerById(Long trainerId) {

        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();

        return convertToResponseDto(trainer);
    }

    @Override
    public List<TrainerResponseDto> getAllTrainers() {

        List<Trainer> trainers = trainerRepository.findAll();

        List<TrainerResponseDto> responseDtos = new ArrayList<>();

        for (Trainer trainer : trainers) {
            responseDtos.add(convertToResponseDto(trainer));
        }

        return responseDtos;
    }

    @Override
    public TrainerResponseDto updateTrainer(Long trainerId, TrainerRequestDto requestDto) {

        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();

        trainer.setName(requestDto.getName());
        trainer.setEmail(requestDto.getEmail());
        trainer.setPhoneNumber(requestDto.getPhoneNumber());
        trainer.setSpecialization(requestDto.getSpecialization());
        trainer.setYearsOfExperience(requestDto.getYearsOfExperience());

        Trainer updatedTrainer = trainerRepository.save(trainer);

        return convertToResponseDto(updatedTrainer);
    }

    @Override
    public boolean deleteTrainer(Long trainerId) {

        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();

        trainerRepository.delete(trainer);

        return true;
    }

    private TrainerResponseDto convertToResponseDto(
            Trainer trainer) {

        return new TrainerResponseDto(
                trainer.getTrainerId(),
                trainer.getName(),
                trainer.getEmail(),
                trainer.getPhoneNumber(),
                trainer.getSpecialization(),
                trainer.getYearsOfExperience(),
                trainer.getCreatedAt(),
                trainer.getUpdatedAt());
    }
}