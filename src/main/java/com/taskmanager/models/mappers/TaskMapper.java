package com.taskmanager.models.mappers;

import com.taskmanager.models.dtos.save.TaskDtoSave;
import com.taskmanager.models.dtos.save.TaskUpdateDto;
import com.taskmanager.models.dtos.send.TaskDtoSend;
import com.taskmanager.models.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDtoSave toTaskDtoSave(Task task);
    @Mappings(
            @Mapping(source = "user.username", target = "userName")
    )
    TaskDtoSend toTaskDtoSend(Task task);
    Task toTask(TaskDtoSave taskDtoSave);
    Task toTask(TaskDtoSend taskDtoSend);
    Task updateTask(TaskUpdateDto taskUpdateDto);
}
