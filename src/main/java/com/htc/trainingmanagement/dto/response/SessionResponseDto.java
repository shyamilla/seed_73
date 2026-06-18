package com.htc.trainingmanagement.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.htc.trainingmanagement.enums.SessionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDto {

    private Long sessionId;
    private String sessionTitle;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String topic;
    private SessionStatus sessionStatus;
    private Long trainingBatchId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
