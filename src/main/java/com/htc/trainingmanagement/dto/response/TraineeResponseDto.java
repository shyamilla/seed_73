package com.htc.trainingmanagement.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.htc.trainingmanagement.enums.TraineeStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeResponseDto {

     private Long traineeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
    private String designation;
    private LocalDate joiningDate;
    private TraineeStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
