package com.htc.trainingmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.TrainingBatch;
import com.htc.trainingmanagement.enums.BatchStatus;

public interface TrainingBatchRepository extends JpaRepository<TrainingBatch, Long> {

    List<TrainingBatch> findByCourseCourseId(Long courseId);

    List<TrainingBatch> findByTrainerTrainerId(Long trainerId);

    List<TrainingBatch> findByStatus(BatchStatus status);
}