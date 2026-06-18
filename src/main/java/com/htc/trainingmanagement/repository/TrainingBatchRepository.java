package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.TrainingBatch;

public interface TrainingBatchRepository extends JpaRepository<TrainingBatch, Long> {

}
