package com.taskmanager.services.Impl;

import com.taskmanager.models.dtos.auth.LoginDto;
import com.taskmanager.models.dtos.auth.ResponseAuth;
import com.taskmanager.models.dtos.save.UserDtoSave;
import com.taskmanager.models.entities.Role;
import com.taskmanager.models.entities.User;
import com.taskmanager.models.enums.ERole;
import com.taskmanager.models.mappers.UserMapper;
import com.taskmanager.repositories.RoleRepository;
import com.taskmanager.repositories.UserRepository;
import com.taskmanager.security.jwt.JwtUtils;
import com.taskmanager.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseAuth login(LoginDto loginDto) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                                                                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        User usuario = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String jwt = jwtUtils.getToken(usuario);

        return ResponseAuth.builder().token(jwt).build();
    }

    @Override
    public String register(UserDtoSave userDtoSave) {
        if (userRepository.existsByEmail(userDtoSave.getEmail()))
            throw new RuntimeException("Email ya registrado");
        if (userRepository.existsByName(userDtoSave.getUserName()))
            throw new RuntimeException("Nombre de usuario ya registrado");

        Set<Role> roles = userDtoSave.getRole().stream()
                .map(roleName -> {
                    ERole eRole = ERole.valueOf(roleName);
                        return roleRepository.findByName(eRole).orElseGet(
                                () -> roleRepository.save(Role.builder().name(eRole).build()));
                }).collect(Collectors.toSet());

        User user = userMapper.toUser(userDtoSave);
        user.setRoles(roles);
        user.setPassword(encoder.encode(userDtoSave.getPassword()));
        userRepository.save(user);
        return "Usuario registrado con éxito";
    }

}
