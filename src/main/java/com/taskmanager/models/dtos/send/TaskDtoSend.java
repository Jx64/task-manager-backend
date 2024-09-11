package com.taskmanager.models.dtos.send;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDtoSend {
    private Long id;
    private String name;
    private String description;
    private String start_date;
    private String end_date;
}
