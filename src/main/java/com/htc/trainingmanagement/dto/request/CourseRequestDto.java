package com.htc.trainingmanagement.dto.request;

import com.htc.trainingmanagement.enums.CourseStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {

    @NotBlank(message = "Course name is required")
    @Size(min = 3, max = 30, message = "Course name must be between 3 and 30 characters")
    private String courseName;

    @NotBlank(message = "Description must not be empty")
    private String description;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer durationInDays;

    @NotNull(message = "Max capacity is required")
    @Positive(message = "Max capacity must be greater than 0")
    private Integer maxCapacity;

    @NotNull(message = "Status is required")
    private CourseStatus status;
}