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
import org.hibernate.annotations.SQLDelete;

@Entity
// Marks this class as a JPA entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"trainee", "trainingBatch"})
// Maps this entity to the enrollment table
@Table(name = "655_enrollment")
// Performs a soft delete by setting is_active to false
@SQLDelete(sql = "UPDATE 655_enrollment SET is_active = false WHERE enrollment_id = ?")
public class Enrollment extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    // Stores the date on which the trainee enrolled
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    // Stores the trainee's completion status as a string
    @Enumerated(EnumType.STRING)
    @Column(name = "completion_status", nullable = false)
    private EnrollmentStatus completionStatus;

    // Stores the trainee's assessment score
    @Column(name = "score")
    private Double score;

    // Stores trainer feedback for the trainee
    @Column(name = "feedback")
    private String feedback;

    // Multiple enrollments can belong to the same trainee
    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    // Multiple enrollments can belong to the same training batch
    @ManyToOne
    @JoinColumn(name = "training_batch_id", nullable = false)
    private TrainingBatch trainingBatch;
}