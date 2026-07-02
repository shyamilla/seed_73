package com.htc.trainingmanagement.entity;

import java.time.LocalDate;

import com.htc.trainingmanagement.enums.TraineeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
// Maps this entity to the trainee table
@Table(name = "665_trainee")
// Performs a soft delete by setting is_active to false
@SQLDelete(sql = "UPDATE 665_trainee SET is_active = false WHERE trainee_id = ?")
public class Trainee extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainee_id")
    private Long traineeId;

    // Stores the trainee's contact number
    @Column(name = "trainee_phone_number")
    private String phoneNumber;

    // Stores the department the trainee belongs to
    @Column(name = "trainee_department")
    private String department;

    // Stores the trainee's job designation
    @Column(name = "designation")
    private String designation;

    // Stores the trainee's joining date
    @Column(name = "joining_date")
    private LocalDate joiningDate;

    // Stores the trainee's current status as a string
    @Enumerated(EnumType.STRING)
    @Column(name = "trainee_status")
    private TraineeStatus status;

    // Each trainee is linked to exactly one user account
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}