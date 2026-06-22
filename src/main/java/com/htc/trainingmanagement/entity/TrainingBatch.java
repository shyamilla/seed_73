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
@Table(name = "665_training_batch") 
public class TrainingBatch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_batch_id") 
    private Long trainingbatchId;

    @Column(name = "batch_code") 
    private String batchCode;

    @Column(name = "batch_name") 
    private String batchName;

    @Column(name = "start_date") 
    private LocalDate startDate;

    @Column(name = "end_date") 
    private LocalDate endDate;

    @Column(name = "capacity") 
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status") 
    private BatchStatus status;

    @ManyToOne // many batches belong to one course
    @JoinColumn(name = "course_id") // foreign key referencing Course table
    private Course course;

    @ManyToOne // many batches belong to one trainer
    @JoinColumn(name = "trainer_id") // foreign key referencing Trainer table
    private Trainer trainer;
}
