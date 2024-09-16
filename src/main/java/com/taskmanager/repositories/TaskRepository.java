package com.taskmanager.repositories;

import com.taskmanager.models.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByUserId(Long userId, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.status.id = :statusId")
    List<Task> findTasksByStatusId(@Param("statusId") Long statusId);
}
