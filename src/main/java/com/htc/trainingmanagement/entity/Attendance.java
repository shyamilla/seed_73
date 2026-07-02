package com.htc.trainingmanagement.entity;

import java.time.LocalDate;

import com.htc.trainingmanagement.enums.AttendanceStatus;

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
@ToString(exclude = {"trainee", "session"})
// Maps this entity to the attendance table
@Table(name = "655_attendance")
// Performs a soft delete by setting is_active to false
@SQLDelete(sql = "UPDATE 655_attendance SET is_active = false WHERE attendance_id = ?")
public class Attendance extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long attendanceId;

    // Stores the attendance date
    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    // Stores the attendance status as a string
    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_status", nullable = false)
    private AttendanceStatus attendanceStatus;

    // Stores optional remarks for the attendance record
    @Column(name = "remarks")
    private String remarks;

    // Multiple attendance records can belong to the same trainee
    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    // Multiple attendance records can belong to the same session
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
}