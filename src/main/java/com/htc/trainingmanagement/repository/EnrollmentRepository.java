package com.htc.trainingmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Enrollment;
import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.entity.TrainingBatch;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

        boolean existsByTraineeAndTrainingBatch(
                        Trainee trainee,
                        TrainingBatch trainingBatch);

        long countByTrainingBatch(
                        TrainingBatch trainingBatch);

        List<Enrollment> findByTraineeTraineeId(Long traineeId);

        List<Enrollment> findByTrainingBatchTrainingbatchId(Long trainingBatchId);
}