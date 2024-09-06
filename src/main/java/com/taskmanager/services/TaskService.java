package com.taskmanager.services;

import com.taskmanager.models.dtos.save.TaskDtoSave;
import com.taskmanager.models.dtos.save.TaskUpdateDto;
import com.taskmanager.models.dtos.send.TaskDtoSend;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<TaskDtoSend> findAll(int page, int size);
    TaskDtoSend findById(Long id);
    TaskDtoSend save(TaskDtoSave taskDtoSave);
    TaskDtoSend update(Long id, TaskUpdateDto taskUpdateDto);
    void delete(Long id);
    void deleteAll();
}
