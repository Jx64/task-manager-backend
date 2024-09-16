package com.taskmanager.services;

import com.taskmanager.models.dtos.auth.LoginDto;
import com.taskmanager.models.dtos.auth.ResponseAuth;
import com.taskmanager.models.dtos.save.UserDtoSave;

public interface AuthService {
    ResponseAuth login(LoginDto loginDto);
    String register(UserDtoSave userDtoSave);
}
