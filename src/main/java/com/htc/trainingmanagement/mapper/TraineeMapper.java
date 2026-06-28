package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.TraineeRequestDto;
import com.htc.trainingmanagement.dto.response.TraineeResponseDto;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.entity.User;

@Component
public class TraineeMapper {

    public Trainee toEntity(User user) {

        Trainee trainee = new Trainee();
        trainee.setUser(user);

        return trainee;
    }

    public TraineeResponseDto toResponseDto(Trainee trainee) {

        return new TraineeResponseDto(
                trainee.getTraineeId(),
                trainee.getUser().getUserId(),
                trainee.getUser().getUserName(),
                trainee.getUser().getEmail(),
                trainee.getPhoneNumber(),
                trainee.getDepartment(),
                trainee.getDesignation(),
                trainee.getJoiningDate(),
                trainee.getStatus(),
                trainee.getCreatedAt(),
                trainee.getUpdatedAt());
    }

    public void updateEntity(Trainee trainee, TraineeRequestDto requestDto) {

        trainee.setPhoneNumber(requestDto.getPhoneNumber());
        trainee.setDepartment(requestDto.getDepartment());
        trainee.setDesignation(requestDto.getDesignation());
        trainee.setJoiningDate(requestDto.getJoiningDate());
        trainee.setStatus(requestDto.getStatus());
    }
}