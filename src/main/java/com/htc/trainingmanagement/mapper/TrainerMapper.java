package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;
import com.htc.trainingmanagement.entity.Trainer;

@Component
public class TrainerMapper {

    public Trainer toEntity(TrainerRequestDto requestDto) {

        Trainer trainer = new Trainer();
        trainer.setTrainerName(requestDto.getTrainerName());
        trainer.setEmail(requestDto.getEmail());
        trainer.setPhoneNumber(requestDto.getPhoneNumber());
        trainer.setSpecialization(requestDto.getSpecialization());
        trainer.setYearsOfExperience(requestDto.getYearsOfExperience());
        return trainer;
    }

    public TrainerResponseDto toResponseDto(Trainer trainer) {
        return new TrainerResponseDto(
                trainer.getTrainerId(),
                trainer.getTrainerName(),
                trainer.getEmail(),
                trainer.getPhoneNumber(),
                trainer.getSpecialization(),
                trainer.getYearsOfExperience(),
                trainer.getCreatedAt(),
                trainer.getUpdatedAt());
    }

    public void updateEntity(Trainer trainer, TrainerRequestDto requestDto) {
        trainer.setTrainerName(requestDto.getTrainerName());
        trainer.setEmail(requestDto.getEmail());
        trainer.setPhoneNumber(requestDto.getPhoneNumber());
        trainer.setSpecialization(requestDto.getSpecialization());
        trainer.setYearsOfExperience(requestDto.getYearsOfExperience());
    }
}
