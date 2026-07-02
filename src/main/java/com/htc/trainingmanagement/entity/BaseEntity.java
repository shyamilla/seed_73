package com.htc.trainingmanagement.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
// Makes this class a parent entity whose fields are inherited by all entities
// Enables automatic auditing (created/updated timestamps and users)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    // Automatically stores the record creation timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    // Automatically updates the record modification timestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    // Stores the username of the user who created the record
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    // Stores the username of the user who last modified the record
    @Column(nullable = false)
    private String updatedBy;

    // Indicates whether the record is active (used for soft delete)
    // Default is true so every newly created record is active
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @PrePersist
    // Ensures isActive is true before inserting if no value is provided
    public void prePersist() {
        if (isActive == null) {
            isActive = true;
        }
    }
}