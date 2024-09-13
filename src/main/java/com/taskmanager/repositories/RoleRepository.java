package com.taskmanager.repositories;

import com.taskmanager.models.entities.Role;
import com.taskmanager.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
