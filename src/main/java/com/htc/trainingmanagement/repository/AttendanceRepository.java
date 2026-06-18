package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{

}
