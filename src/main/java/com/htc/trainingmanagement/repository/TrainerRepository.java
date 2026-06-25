package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.entity.User;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    boolean existsByUser(User user);
}
