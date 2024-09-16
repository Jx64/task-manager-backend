package com.taskmanager.controllers;

import com.taskmanager.models.dtos.send.TaskStatusDtoSend;
import com.taskmanager.services.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Estados de Tareas", description = "Recurso para gestionar los estados de las tareas en el sistema")
@RestController
@RequestMapping("/AppTask/V1.0.0/tasks-status")
@AllArgsConstructor
@Validated
public class TaskStatusController {

    private TaskStatusService taskStatusService;

    @Operation(summary = "Obtener todos los estados de las tareas", description = "Devuelve una lista de todos los estados posibles de las tareas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados obtenida correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso prohibido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TaskStatusDtoSend>> getAllTaskStatuses() {
        List<TaskStatusDtoSend> taskStatuses = taskStatusService.GetTaskStatuses();
        return ResponseEntity.ok(taskStatuses);
    }

}
