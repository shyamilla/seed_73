package com.htc.trainingmanagement.dto.response;

import java.time.LocalDateTime;

import com.htc.trainingmanagement.enums.CourseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {

    private Long courseId;
    private String courseName;
    private String description;
    private Integer durationInDays;
    private Integer maxCapacity;
    private CourseStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
