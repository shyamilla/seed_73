package com.htc.trainingmanagement.entity;

import com.htc.trainingmanagement.enums.CourseStatus;

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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "665_course")
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId; 

    private String courseName; 

    private String description; 

    private Integer durationInDays; 

    private Integer maxCapacity; // Maximum number of trainees allowed

    @Enumerated(EnumType.STRING)
    private CourseStatus status; 
}