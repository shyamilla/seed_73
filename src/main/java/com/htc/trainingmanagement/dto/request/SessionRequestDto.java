package com.htc.trainingmanagement.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.htc.trainingmanagement.enums.SessionStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionRequestDto {

    @NotBlank(message = "Session title is required")
    private String sessionTitle;

    @NotNull(message = "Session date is required")
    private LocalDate sessionDate;

    @NotNull(message = "Session start time is required")
    private LocalTime startTime;

    @NotNull(message = "Session end time is required")
    private LocalTime endTime;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotNull(message = "Session status is required")
    private SessionStatus sessionStatus;

    @NotNull(message = "Training batch ID is required")
    private Long trainingBatchId;
}