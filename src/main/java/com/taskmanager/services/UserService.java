package com.taskmanager.services;

import com.taskmanager.models.dtos.send.UserDtoSend;
import org.springframework.data.domain.Page;

public interface UserService {
    String updateRole(String email, String role, String userContext);
    Page<UserDtoSend> getAllUsers(int page, int size);
}
