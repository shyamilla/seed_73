package com.htc.trainingmanagement.entity;

import java.time.LocalDate;

import com.htc.trainingmanagement.enums.BatchStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@ToString(exclude = { "course", "trainer" })
// Maps this entity to the training batch table
@Table(name = "665_training_batch")
// Performs a soft delete by setting is_active to false
@SQLDelete(sql = "UPDATE 665_training_batch SET is_active = false WHERE trainingbatch_id = ?")
public class TrainingBatch extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_batch_id")
    private Long trainingbatchId;

    // Stores the unique batch code
    @Column(name = "batch_code", nullable = false, unique = true)
    private String batchCode;

    // Stores the batch name
    @Column(name = "batch_name", nullable = false)
    private String batchName;

    // Stores the batch start date
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // Stores the batch end date
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // Stores the maximum number of trainees allowed
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    // Stores the remaining seats available for enrollment
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    // Stores the current batch status as a string
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BatchStatus status;

    // Multiple batches can be created for the same course
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // A trainer can be assigned to multiple training batches
    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @PrePersist
    public void initializeAvailableSeats() {
        if (availableSeats == null) {
            availableSeats = capacity;
        }
    }
}