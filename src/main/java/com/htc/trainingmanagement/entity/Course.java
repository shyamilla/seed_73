package com.htc.trainingmanagement.entity;

import org.hibernate.annotations.SQLDelete;

import com.htc.trainingmanagement.enums.CourseStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
// Marks this class as a JPA entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
// Maps this entity to the course table
@Table(name = "665_course")
// Performs a soft delete by setting is_active to false
@SQLDelete(sql = "UPDATE 665_course SET is_active = false WHERE course_id = ?")
public class Course extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    // Stores the course name
    @Column(name = "course_name", nullable = false)
    private String courseName;

    // Stores the course description
    @Column(name = "description")
    private String description;

    // Stores the course duration in days
    @Column(name = "course_duration", nullable = false)
    private Integer durationInDays;

    // Stores the maximum number of trainees allowed
    @Column(name = "capacity", nullable = false)
    private Integer maxCapacity;

    // Stores the current course status as a string
    @Enumerated(EnumType.STRING)
    @Column(name = "course_status", nullable = false)
    private CourseStatus status;
}