package com.taskmanager.services.Impl;

import com.taskmanager.models.dtos.send.UserDtoSend;
import com.taskmanager.models.entities.Role;
import com.taskmanager.models.entities.User;
import com.taskmanager.models.enums.ERole;
import com.taskmanager.models.mappers.UserMapper;
import com.taskmanager.repositories.RoleRepository;
import com.taskmanager.repositories.UserRepository;
import com.taskmanager.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    @Override
    public String updateRole(String email, String role, String userContext) {
        if (userContext.equals(email)){
            throw new RuntimeException("Cule liso! No puedes cambiarte el rol tú mismo!");
        }
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found"));
        Role role1 = roleRepository.findByName(
                ERole.valueOf(role)).orElseGet(
                () -> roleRepository.save(Role.builder()
                        .name(ERole.valueOf(role))
                        .build()));
        user.setRoles(new HashSet<>(Set.of(role1)));
        userRepository.save(user);
        return "Role updated a " + role1.getName();
    }

    @Override
    public Page<UserDtoSend> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }
}
