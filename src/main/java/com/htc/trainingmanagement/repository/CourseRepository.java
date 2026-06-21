package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseName(String courseName);
}
