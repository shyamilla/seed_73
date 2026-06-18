package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Trainee;

public interface TraineeRepository extends JpaRepository<Trainee, Long>{

}
