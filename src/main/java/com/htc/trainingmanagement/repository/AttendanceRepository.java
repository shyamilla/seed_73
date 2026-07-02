package com.htc.trainingmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Attendance;
import com.htc.trainingmanagement.entity.Session;
import com.htc.trainingmanagement.entity.Trainee;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByTraineeTraineeId(Long traineeId);

    List<Attendance> findBySessionSessionId(Long sessionId);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    boolean existsByTraineeAndSession(Trainee trainee, Session session); 


}