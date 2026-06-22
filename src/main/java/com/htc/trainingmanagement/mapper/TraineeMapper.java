package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;
import com.htc.trainingmanagement.entity.Trainee;

@Component
public class TraineeMapper {

    public Trainee toEntity(TraineeRequestDto requestDto) {

        Trainee trainee = new Trainee();

        trainee.setFirstName(requestDto.getFirstName());
        trainee.setLastName(requestDto.getLastName());
        trainee.setEmail(requestDto.getEmail());
        trainee.setPhoneNumber(requestDto.getPhoneNumber());
        trainee.setDepartment(requestDto.getDepartment());
        trainee.setDesignation(requestDto.getDesignation());
        trainee.setJoiningDate(requestDto.getJoiningDate());
        trainee.setStatus(requestDto.getStatus());

        return trainee;
    }

    public TraineeResponseDto toResponseDto(Trainee trainee) {

        return new TraineeResponseDto(
                trainee.getTraineeId(),
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getEmail(),
                trainee.getPhoneNumber(),
                trainee.getDepartment(),
                trainee.getDesignation(),
                trainee.getJoiningDate(),
                trainee.getStatus(),
                trainee.getCreatedAt(),
                trainee.getUpdatedAt());
    }

    public void updateEntity(
            Trainee trainee,
            TraineeRequestDto requestDto) {

        trainee.setFirstName(requestDto.getFirstName());
        trainee.setLastName(requestDto.getLastName());
        trainee.setEmail(requestDto.getEmail());
        trainee.setPhoneNumber(requestDto.getPhoneNumber());
        trainee.setDepartment(requestDto.getDepartment());
        trainee.setDesignation(requestDto.getDesignation());
        trainee.setJoiningDate(requestDto.getJoiningDate());
        trainee.setStatus(requestDto.getStatus());
    }
}