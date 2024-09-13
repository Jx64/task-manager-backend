package com.taskmanager.models.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAuth {
    private String token;
}
