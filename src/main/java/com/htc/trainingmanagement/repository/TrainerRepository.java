package com.htc.trainingmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Trainer;
import com.htc.trainingmanagement.entity.User;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    boolean existsByUser(User user);

    List<Trainer> findBySpecializationIgnoreCase(String specialization);

    List<Trainer> findByYearsOfExperienceGreaterThanEqual(Integer yearsOfExperience);


    Optional<Trainer> findByUserEmail(String email);
}