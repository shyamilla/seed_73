package com.htc.trainingmanagement.entity;

import java.time.LocalDateTime;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public abstract class BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    // private Boolean isActive = true;
    // @PrePersist
    // protected void onCreate() {
    // createdAt = LocalDateTime.now();
    // updatedAt = LocalDateTime.now();
    // isActive = true;
    // }

    // @PreUpdate
    // protected void onUpdate() {
    // updatedAt = LocalDateTime.now();

    // }
}
