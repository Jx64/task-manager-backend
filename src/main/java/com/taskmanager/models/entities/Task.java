package com.taskmanager.models.entities;

import com.taskmanager.models.enums.Priority;
import com.taskmanager.models.enums.TasksState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private LocalDate date;
    private LocalDate deadline;
    private LocalDate executionDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TasksState state;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    //Relationships


    public Task updateTask(Task task){
        return new Task(this.id, task.getName(), task.getDescription(),
                task.getDate(), task.getDeadline(), task.getExecutionDate(),
                task.getState(), task.getPriority());
    }
}
