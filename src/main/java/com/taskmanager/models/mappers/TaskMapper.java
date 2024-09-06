package com.taskmanager.models.mappers;

import com.taskmanager.models.dtos.save.TaskDtoSave;
import com.taskmanager.models.dtos.save.TaskUpdateDto;
import com.taskmanager.models.dtos.send.TaskDtoSend;
import com.taskmanager.models.entities.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDtoSend toTaskDtoSend(Task task);
    TaskDtoSave toTaskDtoSave(Task task);
    Task toTask(TaskDtoSave taskDtoSave);
    Task toTask(TaskDtoSend taskDtoSend);

    Task updateTask(TaskUpdateDto taskUpdateDto);
}
