package com.taskmanager.services;

import com.taskmanager.models.dtos.send.TaskStatusDtoSend;
import com.taskmanager.models.entities.Task;
import com.taskmanager.models.entities.TaskStatus;

import java.util.List;

public interface TaskStatusService {

    List<TaskStatusDtoSend> GetTaskStatuses();
}
