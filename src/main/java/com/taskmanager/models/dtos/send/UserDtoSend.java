package com.taskmanager.models.dtos.send;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoSend {
    private Long id;
    private String userName;
    private String email;
    private Set<String> roles;
    private List<TaskDtoSend> tasks;
}
