package com.htc.trainingmanagement.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.htc.trainingmanagement.enums.EnrollmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponseDto {

    private Long enrollmentId;
    private LocalDate enrollmentDate;
    private EnrollmentStatus completionStatus;
    private Double score;
    private String feedback;

    private Long traineeId;
    private String traineeName;

    private Long trainingBatchId;
    private String trainingBatchName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}