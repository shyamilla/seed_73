package com.htc.trainingmanagement.dto.response;

import com.htc.trainingmanagement.enums.EnrollmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerTraineeResponseDto {
// return trainee information to the logged-in trainer.
    private Long traineeId;

    private String traineeName;

    private String email;

    private String phoneNumber;

    private String batchName;

    private String department;

    private EnrollmentStatus completionStatus;

    private Double score;
}
