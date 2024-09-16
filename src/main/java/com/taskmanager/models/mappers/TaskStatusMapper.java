package com.taskmanager.models.mappers;

import com.taskmanager.models.dtos.send.TaskStatusDtoSend;
import com.taskmanager.models.entities.TaskStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskStatusMapper {
    TaskStatus taskStatusDtoSendToTaskStatusEntity(TaskStatusDtoSend taskStatusDtoSend);
    TaskStatusDtoSend taskStatusEntityToTaskStatusDtoSend(TaskStatus taskStatus);

}
