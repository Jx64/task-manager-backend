package com.taskmanager.controllers;

import com.taskmanager.models.dtos.save.TaskDtoSave;
import com.taskmanager.models.dtos.save.TaskUpdateDto;
import com.taskmanager.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/AppTask/V1.0.0/tasks")
@AllArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(taskService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(taskService.findById(id));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TaskDtoSave taskDtoSave){
        return ResponseEntity.ok(taskService.save(taskDtoSave));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TaskUpdateDto taskUpdateDto){
        return ResponseEntity.ok(taskService.update(id, taskUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok("Task deleted");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        taskService.deleteAll();
        return ResponseEntity.ok("All tasks deleted");
    }
}
