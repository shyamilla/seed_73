package com.htc.trainingmanagement.dto.request;

import com.htc.trainingmanagement.enums.CourseStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseRequestDto {

    @NotBlank(message = "Trainer name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String courseName;
    @NotBlank(message = "Description must not be empty")
    private String description;
    @NotNull(message = "This field must not be blank ")
    @PositiveOrZero(message = "Duration must eithr be zero or Positive")
    private Integer durationInDays;
    @NotNull(message = "Max capacity is required")
    @Positive(message = "Max capacity must be greater than 0")
    private Integer maxCapacity;
    @NotNull(message = "Status is required")
    private CourseStatus status;

}
