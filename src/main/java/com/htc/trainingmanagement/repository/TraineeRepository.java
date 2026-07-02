package com.htc.trainingmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Trainee;
import com.htc.trainingmanagement.entity.User;
import com.htc.trainingmanagement.enums.TraineeStatus;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    boolean existsByUser(User user);

    List<Trainee> findByDepartmentIgnoreCase(String department);

    List<Trainee> findByStatus(TraineeStatus status);

    Optional<Trainee> findByUserEmail(String email);

    List<Trainee> findByIsActiveFalse();

    List<Trainee> findByIsActiveTrue();

    
}