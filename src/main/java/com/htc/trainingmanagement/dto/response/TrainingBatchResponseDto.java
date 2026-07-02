package com.htc.trainingmanagement.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.htc.trainingmanagement.enums.BatchStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingBatchResponseDto {

    private Long trainingBatchId;
    private String batchCode;
    private String batchName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
    private Integer availableSeats;
    private BatchStatus status;

    private Long courseId;
    private String courseName;

    private Long trainerId;
    private String trainerName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}