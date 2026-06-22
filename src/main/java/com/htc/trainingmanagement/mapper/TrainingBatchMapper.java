package com.htc.trainingmanagement.mapper;

import org.springframework.stereotype.Component;

import com.htc.trainingmanagement.dto.request.TrainingBatchRequestDto;
import com.htc.trainingmanagement.dto.response.TrainingBatchResponseDto;
import com.htc.trainingmanagement.entity.Course;
import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.entity.TrainingBatch;

@Component
public class TrainingBatchMapper {

    public TrainingBatch toEntity(TrainingBatchRequestDto requestDto) {

        TrainingBatch trainingBatch = new TrainingBatch();
        Course course = new Course();
        course.setCourseId(requestDto.getCourseId());
        Trainer trainer = new Trainer();
        trainer.setTrainerId(requestDto.getTrainerId());

        trainingBatch.setBatchCode(requestDto.getBatchCode());
        trainingBatch.setBatchName(requestDto.getBatchName());
        trainingBatch.setStartDate(requestDto.getStartDate());
        trainingBatch.setEndDate(requestDto.getEndDate());
        trainingBatch.setCapacity(requestDto.getCapacity());
        trainingBatch.setStatus(requestDto.getStatus());
        trainingBatch.setCourse(course);
        trainingBatch.setTrainer(trainer);

        return trainingBatch;
    }

    public void updateEntity(
            TrainingBatch trainingBatch,
            TrainingBatchRequestDto requestDto) {

        Course course = new Course();
        course.setCourseId(requestDto.getCourseId());

        Trainer trainer = new Trainer();
        trainer.setTrainerId(requestDto.getTrainerId());

        trainingBatch.setBatchCode(requestDto.getBatchCode());
        trainingBatch.setBatchName(requestDto.getBatchName());
        trainingBatch.setStartDate(requestDto.getStartDate());
        trainingBatch.setEndDate(requestDto.getEndDate());
        trainingBatch.setCapacity(requestDto.getCapacity());
        trainingBatch.setStatus(requestDto.getStatus());
        trainingBatch.setCourse(course);
        trainingBatch.setTrainer(trainer);
    }

    public TrainingBatchResponseDto toResponseDto(
            TrainingBatch trainingBatch) {

        return new TrainingBatchResponseDto(
                trainingBatch.getTrainingbatchId(),
                trainingBatch.getBatchCode(),
                trainingBatch.getBatchName(),
                trainingBatch.getStartDate(),
                trainingBatch.getEndDate(),
                trainingBatch.getCapacity(),
                trainingBatch.getStatus(),
                trainingBatch.getCourse().getCourseId(),
                trainingBatch.getCourse().getCourseName(),
                trainingBatch.getTrainer().getTrainerId(),
                trainingBatch.getTrainer().getTrainerName(),
                trainingBatch.getCreatedAt(),
                trainingBatch.getUpdatedAt());
    }
}