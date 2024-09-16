package com.taskmanager.services.Impl;

import com.taskmanager.models.dtos.send.TaskStatusDtoSend;
import com.taskmanager.models.entities.Task;
import com.taskmanager.models.entities.TaskStatus;
import com.taskmanager.models.mappers.TaskStatusMapper;
import com.taskmanager.repositories.TaskStatusRepository;
import com.taskmanager.services.TaskService;
import com.taskmanager.services.TaskStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {

    private TaskStatusRepository taskStatusRepository;
    private TaskStatusMapper taskStatusMapper;

    public TaskStatusServiceImpl(TaskStatusRepository taskStatusRepository, TaskStatusMapper taskStatusMapper) {
        this.taskStatusRepository = taskStatusRepository;
        this.taskStatusMapper = taskStatusMapper;
    }

    @Override
    public List<TaskStatusDtoSend> GetTaskStatuses() {
        List<TaskStatusDtoSend> taskStatusDto = this.taskStatusRepository.findAll()
                .stream().map(status -> this.taskStatusMapper.taskStatusEntityToTaskStatusDtoSend(status))
                .collect(Collectors.toList());
        return taskStatusDto;
    }
}
