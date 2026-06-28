package com.htc.trainingmanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByTrainingBatchTrainingbatchId(Long trainingBatchId);

    List<Session> findBySessionDate(LocalDate sessionDate);
}