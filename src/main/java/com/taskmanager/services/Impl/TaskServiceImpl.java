package com.taskmanager.services.Impl;

import com.taskmanager.models.dtos.save.TaskDtoSave;
import com.taskmanager.models.dtos.save.TaskUpdateDto;
import com.taskmanager.models.dtos.send.TaskDtoSend;
import com.taskmanager.models.entities.Task;
import com.taskmanager.models.entities.TaskStatus;
import com.taskmanager.models.entities.User;
import com.taskmanager.models.mappers.TaskMapper;
import com.taskmanager.models.mappers.TaskStatusMapper;
import com.taskmanager.repositories.TaskRepository;
import com.taskmanager.repositories.TaskStatusRepository;
import com.taskmanager.repositories.UserRepository;
import com.taskmanager.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusMapper taskStatusMapper;
    @Override
    public Page<TaskDtoSend> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable).map(taskMapper::toTaskDtoSend);
    }

    @Override
    public TaskDtoSend findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Task not found"));
        return taskMapper.toTaskDtoSend(task);
    }

    @Override
    public Page<TaskDtoSend> findByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));

        return taskRepository.findAllByUserId(user.getId(), pageable)
                .map(taskMapper::toTaskDtoSend);
    }

    @Override
    public TaskDtoSend save(TaskDtoSave taskDtoSave, String userEmail) {
        TaskStatus taskStatus = taskStatusRepository.findByName(taskDtoSave.getStatus().getName());
        if (isNull(taskStatus)){
            taskStatus = taskStatusRepository.save(taskStatusMapper.taskStatusDtoSaveToEntity(taskDtoSave.getStatus()));
        }
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new RuntimeException("User not found"));
        Task task = taskMapper.toTask(taskDtoSave);
        task.setUser(user);
        task.setStatus(taskStatus);
        return taskMapper.toTaskDtoSend(taskRepository.save(task));
    }

    @Override
    public TaskDtoSend update(Long id, TaskUpdateDto taskUpdateDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new RuntimeException("User not found"));
        TaskStatus taskStatus = taskStatusRepository.findByName(taskUpdateDto.getStatus().getName());
        if (isNull(taskStatus)) throw new RuntimeException("Task status not found");

        Optional<Task> task = taskRepository.findById(id);
        Task taskO = taskMapper.updateTask(taskUpdateDto);
        taskO.setUser(user);
        taskO.setStatus(taskStatus);
        if(task.isEmpty()){
            return taskMapper.toTaskDtoSend(taskRepository.save(taskO));
        }
        Task taskToUpdate = task.get().updateTask(taskO);
        return taskMapper.toTaskDtoSend(taskRepository.save(taskToUpdate));
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
    }

    @Override
    public List<TaskDtoSend> findTasksByStatusId(Long statusId) {
        List<TaskDtoSend> tasksByStatus = taskRepository.findTasksByStatusId(statusId)
                .stream().map(task -> this.taskMapper.toTaskDtoSend(task))
                .collect(Collectors.toList());
        return tasksByStatus;
    }
}
