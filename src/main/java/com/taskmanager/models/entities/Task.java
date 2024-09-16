package com.taskmanager.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


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
    private LocalDate start_date;
    private LocalDate end_date;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private TaskStatus status;

    public Task updateTask(Task task){
        return new Task(this.id, task.getName(), task.getDescription(),
                task.getStart_date(), task.getEnd_date(), task.getUser(), task.getStatus());
    }
}
