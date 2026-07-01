package com.htc.trainingmanagement.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseAdminResponseDto extends CourseResponseDto {

    private String createdBy;
    private String updatedBy;
}