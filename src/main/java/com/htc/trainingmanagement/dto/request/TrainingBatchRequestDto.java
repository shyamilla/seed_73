package com.htc.trainingmanagement.dto.request;

import java.time.LocalDate;

import com.htc.trainingmanagement.enums.BatchStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingBatchRequestDto {

    @NotBlank(message = "Batch code is required")
    private String batchCode;

    @NotBlank(message = "Batch name is required")
    private String batchName;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be greater than 0")
    private Integer capacity;

    // @PositiveOrZero(message = "Enrolled count cannot be negative")
    // private Integer enrolledCount;

    @NotNull(message = "Status is required")
    private BatchStatus status;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Trainer ID is required")
    private Long trainerId;
}