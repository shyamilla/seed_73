package com.htc.trainingmanagement.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.htc.trainingmanagement.enums.SessionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

@Entity
// Marks this class as a JPA entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "trainingBatch")
// Maps this entity to the session table
@Table(name = "655_session")
// Performs a soft delete by setting is_active to false
@SQLDelete(sql = "UPDATE 655_session SET is_active = false WHERE session_id = ?")
public class Session extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    // Stores the session title
    @Column(name = "session_title", nullable = false)
    private String sessionTitle;

    // Stores the scheduled session date
    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    // Stores the session start time
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    // Stores the session end time
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // Stores the topic covered in the session
    @Column(name = "topic")
    private String topic;

    // Stores the current session status as a string
    @Enumerated(EnumType.STRING)
    @Column(name = "session_status", nullable = false)
    private SessionStatus sessionStatus;

    // Multiple sessions can belong to a single training batch
    @ManyToOne
    @JoinColumn(name = "training_batch_id", nullable = false)
    private TrainingBatch trainingBatch;
}