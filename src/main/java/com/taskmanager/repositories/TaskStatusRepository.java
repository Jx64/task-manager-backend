package com.taskmanager.repositories;

import com.taskmanager.models.entities.Task;
import com.taskmanager.models.entities.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

}
