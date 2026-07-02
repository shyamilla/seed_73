package com.htc.trainingmanagement.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@ToString(exclude = "roles")
// Maps this entity to the users table
@Table(name = "665_users")
// Performs a soft delete by marking the user as inactive
@SQLDelete(sql = "UPDATE 665_users SET is_active = false WHERE user_id = ?")
public class User extends BaseEntity {

    @Id
    // Primary key with auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    // Stores the user's display name
    @Column(name = "user_name", nullable = false)
    private String userName;

    // Stores the user's unique email address used for login
    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    // Stores the encrypted user password
    @Column(name = "password", nullable = false)
    private String password;

    // A user can have multiple roles, and a role can belong to multiple users
    // EAGER fetch loads roles immediately during authentication
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "665_user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}