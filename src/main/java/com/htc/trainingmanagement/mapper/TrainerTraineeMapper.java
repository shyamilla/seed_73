package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.response.TrainerTraineeResponseDto;
import com.htc.trainingmanagement.entity.Enrollment;
import com.htc.trainingmanagement.entity.Trainee;

@Component
public class TrainerTraineeMapper {

    public TrainerTraineeResponseDto toResponseDto(Enrollment enrollment) {

        Trainee trainee = enrollment.getTrainee();

        return new TrainerTraineeResponseDto(

                trainee.getTraineeId(),

                trainee.getUser().getUserName(),

                trainee.getUser().getEmail(),

                trainee.getPhoneNumber(),

                enrollment.getTrainingBatch().getBatchName(),

                trainee.getDepartment(),

                enrollment.getCompletionStatus(),

                enrollment.getScore());
    }
}