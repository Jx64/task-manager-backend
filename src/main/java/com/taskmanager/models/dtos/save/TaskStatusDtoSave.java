package com.taskmanager.models.dtos.save;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStatusDtoSave {
    private String name;
    private String description;
}
