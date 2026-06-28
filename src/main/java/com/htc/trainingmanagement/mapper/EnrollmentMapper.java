package com.htc.trainingmanagement.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.response.EnrollmentResponseDto;
import com.htc.trainingmanagement.entity.Enrollment;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.entity.TrainingBatch;
import com.htc.trainingmanagement.enums.EnrollmentStatus;

@Component
public class EnrollmentMapper {

    public Enrollment toEntity(
            Trainee trainee,
            TrainingBatch trainingBatch) {

        Enrollment enrollment = new Enrollment();

        enrollment.setTrainee(trainee);
        enrollment.setTrainingBatch(trainingBatch);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setCompletionStatus(EnrollmentStatus.ENROLLED);
        enrollment.setScore(0.0);

        return enrollment;
    }

    public void updateEntity(
            Enrollment enrollment,
            Trainee trainee,
            TrainingBatch trainingBatch) {

        enrollment.setTrainee(trainee);
        enrollment.setTrainingBatch(trainingBatch);
    }

    public EnrollmentResponseDto toResponseDto(Enrollment enrollment) {

        return new EnrollmentResponseDto(
                enrollment.getEnrollmentId(),
                enrollment.getEnrollmentDate(),
                enrollment.getCompletionStatus(),
                enrollment.getScore(),
                enrollment.getFeedback(),
                enrollment.getTrainee().getTraineeId(),
                enrollment.getTrainee().getUser().getUserName(),
                enrollment.getTrainingBatch().getTrainingbatchId(),
                enrollment.getTrainingBatch().getBatchName(),
                enrollment.getCreatedAt(),
                enrollment.getUpdatedAt());
    }
}