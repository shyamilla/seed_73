package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.entity.User;

public interface TraineeRepository extends JpaRepository<Trainee, Long>{


    boolean existsByUser(User user);
}
