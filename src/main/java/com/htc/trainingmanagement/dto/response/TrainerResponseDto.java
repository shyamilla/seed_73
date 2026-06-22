package com.htc.trainingmanagement.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerResponseDto {
    private Long trainerId;
    
    private String name;
    private String email;
    private String phoneNumber;
    private String specialization;
    private Integer yearsOfExperience;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  
}
