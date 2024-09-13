package com.taskmanager.services;

import com.taskmanager.models.dtos.save.TaskDtoSave;
import com.taskmanager.models.dtos.save.TaskUpdateDto;
import com.taskmanager.models.dtos.send.TaskDtoSend;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
    Page<TaskDtoSend> findAll(int page, int size);
    TaskDtoSend findById(Long id);
    Page<TaskDtoSend> findByUserId(Long userId, int page, int size);
    TaskDtoSend save(TaskDtoSave taskDtoSave, String userEmail);
    TaskDtoSend update(Long id, TaskUpdateDto taskUpdateDto, String userEmail);
    void delete(Long id);
    void deleteAll();
}
