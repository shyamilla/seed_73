package com.htc.trainingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.htc.trainingmanagement.entity.Role;
import java.util.Optional;

import com.htc.trainingmanagement.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds a role by its role name.
     */
    Optional<Role> findByRoleName(RoleName roleName);

}
