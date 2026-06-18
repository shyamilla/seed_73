package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

}
