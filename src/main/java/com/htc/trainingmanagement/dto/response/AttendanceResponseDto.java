package com.htc.trainingmanagement.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.htc.trainingmanagement.enums.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDto {

    private Long attendanceId;
    private LocalDate attendanceDate;
    private AttendanceStatus attendanceStatus;
    private String remarks;
    private Long traineeId;
    private Long sessionId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
