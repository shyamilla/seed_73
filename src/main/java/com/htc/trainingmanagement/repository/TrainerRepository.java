package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

}
