package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.TrainerRequestDto;
import com.htc.trainingmanagement.dto.response.TrainerResponseDto;
import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.entity.User;

@Component
public class TrainerMapper {

    // public Trainer toEntity(TrainerRequestDto requestDto, User user) {

    //     Trainer trainer = new Trainer();
    //     trainer.setPhoneNumber(requestDto.getPhoneNumber());
    //     trainer.setSpecialization(requestDto.getSpecialization());
    //     trainer.setYearsOfExperience(requestDto.getYearsOfExperience());
    //     trainer.setUser(user);
    //     return trainer;
    // }

    public TrainerResponseDto toResponseDto(Trainer trainer) {
        return new TrainerResponseDto(
                trainer.getTrainerId(),
                trainer.getUser().getUserId(),
                trainer.getUser().getUserName(),
                trainer.getUser().getEmail(),
                trainer.getPhoneNumber(),
                trainer.getSpecialization(),
                trainer.getYearsOfExperience(),
                trainer.getCreatedAt(),
                trainer.getUpdatedAt());
    }

    public void updateEntity(Trainer trainer, TrainerRequestDto requestDto) {

        trainer.setPhoneNumber(requestDto.getPhoneNumber());
        trainer.setSpecialization(requestDto.getSpecialization());
        trainer.setYearsOfExperience(requestDto.getYearsOfExperience());
    }

}
