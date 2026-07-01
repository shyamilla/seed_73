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
// Enables automatic auditing for all entities extending this class
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    // Automatically stores the creation timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    // Automatically updates the last modified timestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    // Stores the user who created the record
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    // Stores the user who last updated the record
    @Column(nullable = false)
    private String updatedBy;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @PrePersist 
    public void prePersist() {
        if (isActive == null) {
            isActive = true;
        }
    }
}