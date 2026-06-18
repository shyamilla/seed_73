package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

}
