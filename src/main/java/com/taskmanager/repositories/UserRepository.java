package com.taskmanager.repositories;

import com.taskmanager.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
}
