package com.taskmanager.models.mappers;

import com.taskmanager.models.dtos.save.UserDtoSave;
import com.taskmanager.models.dtos.send.TaskDtoSend;
import com.taskmanager.models.dtos.send.UserDtoSend;
import com.taskmanager.models.entities.Role;
import com.taskmanager.models.entities.Task;
import com.taskmanager.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings(
            @Mapping(target = "name", source = "userName")
    )
    User toUser(UserDtoSave userDtoSave);
    @Mappings(
            @Mapping(target = "userName", source = "name")
    )
    UserDtoSend toDto(User user);

    @Mappings(
            @Mapping(source = "user.username", target = "userName")
    )
    TaskDtoSend toTaskDtoSend(Task task);

    default Set<String> mapRolesToString(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }
}
