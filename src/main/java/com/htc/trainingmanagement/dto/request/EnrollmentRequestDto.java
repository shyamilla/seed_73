package com.htc.trainingmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequestDto {

    @NotNull(message = "Trainer id must not be null")
    private Long traineeId;
    @NotNull(message = "Training Batch  id must not be null")
    private Long trainingBatchId;
}
