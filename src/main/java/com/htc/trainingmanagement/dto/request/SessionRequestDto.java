package com.htc.trainingmanagement.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.htc.trainingmanagement.enums.SessionStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionRequestDto {

    @NotBlank(message = "Session title should not be null.")
    private String sessionTitle;
    @NotNull(message = "Session date is required.")
    private LocalDate sessionDate;
    @NotNull(message = "Session start time is required.")
    private LocalTime startTime;
    @NotNull(message = "Session end time is required.")
    private LocalTime endTime;
    @NotBlank(message = "Topic for the session is mandatory.")
    private String topic;
    @NotBlank(message = "Session Status is required.")
    private SessionStatus sessionStatus;
    @NotNull(message = "Training Batch ID is required.")
    private Long trainingBatchId;

}
