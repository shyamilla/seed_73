package com.htc.trainingmanagement.dto.request;

import java.time.LocalDate;

import com.htc.trainingmanagement.enums.AttendanceStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequestDto {

    @NotNull(message = "Attendance date is required")
    private LocalDate attendanceDate;
    @NotNull(message = "Attendance status is required")
    private AttendanceStatus attendanceStatus;
    @NotBlank(message = "Attendance status is required")
    private String remarks;
    @NotNull(message = "Trainee Id is required")
    private Long traineeId;
    @NotNull(message = "Session Id is required")
    private Long sessionId;
}
