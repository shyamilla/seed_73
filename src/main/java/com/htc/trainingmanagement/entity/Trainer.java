package com.htc.trainingmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@ToString(exclude = "user")
// Maps this entity to the trainer table
@Table(name = "665_trainer")
// Performs a soft delete by setting is_active to false
@SQLDelete(sql = "UPDATE 665_trainer SET is_active = false WHERE trainer_id = ?")
public class Trainer extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long trainerId;

    // Stores the trainer's contact number
    @Column(name = "phone_number")
    private String phoneNumber;

    // Stores the trainer's area of expertise
    @Column(name = "specialization")
    private String specialization;

    // Stores the trainer's total years of experience
    @Column(name = "experience")
    private Integer yearsOfExperience;

    // Each trainer is linked to exactly one user account
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}