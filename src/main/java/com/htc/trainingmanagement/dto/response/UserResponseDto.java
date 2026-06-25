package com.htc.trainingmanagement.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String userName;
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
