package com.htc.trainingmanagement.entity;

import com.htc.trainingmanagement.enums.RoleName;

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
// Maps this entity to the roles table
@Table(name = "665_roles")
public class Role extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    // Stores the role name as a string
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true, nullable = false)
    private RoleName roleName;
}