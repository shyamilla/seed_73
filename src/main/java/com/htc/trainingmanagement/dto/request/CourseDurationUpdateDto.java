package com.htc.trainingmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CourseDurationUpdateDto {

    @NotNull(message = "Duration is required")
    @PositiveOrZero(message = "Duration must be zero or positive")
    private Integer durationInDays;
}