package com.htc.trainingmanagement.entity;

import java.time.LocalDate;

import com.htc.trainingmanagement.enums.BatchStatus;

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
    private Long trainingbatchId;
    private String batchCode;
    private String batchName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
    private Integer enrolledCount;
    @Enumerated(EnumType.STRING)
    private BatchStatus status;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

}
