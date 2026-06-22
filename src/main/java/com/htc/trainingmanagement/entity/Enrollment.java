package com.htc.trainingmanagement.entity;

import java.time.LocalDate;

import com.htc.trainingmanagement.enums.EnrollmentStatus;

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

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "655_enrollment")
public class Enrollment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "completion_status")
    private EnrollmentStatus completionStatus;

    @Column(name = "score")
    private Double score;

    @Column(name = "feedback")
    private String feedback;

    @ManyToOne // many enrollments belong to one trainee
    @JoinColumn(name = "trainee_id") // foreign key referencing trainee table
    private Trainee trainee;

    @ManyToOne // many enrollments belong to one training batch
    @JoinColumn(name = "training_batch_id") // foreign key referencing training_batch table
    private TrainingBatch trainingBatch;
}
