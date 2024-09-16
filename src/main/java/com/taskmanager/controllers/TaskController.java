package com.taskmanager.controllers;

import com.taskmanager.models.dtos.auth.LoginDto;
import com.taskmanager.models.dtos.save.TaskDtoSave;
import com.taskmanager.models.dtos.save.TaskUpdateDto;
import com.taskmanager.models.dtos.send.TaskDtoSend;
import com.taskmanager.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Tareas", description = "Recurso para gestionar las tareas en el sistema")
@RestController
@RequestMapping("/AppTask/V1.0.0/tasks")
@AllArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;


    @Operation(
            summary = "Obtener todas las tareas",
            description = "El administrador podrá obtener todas las teareas registradas en el sitema",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200")})
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TaskDtoSend>> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(taskService.findAll(page, size));
    }


    @Operation(
            summary = "Obtener tarea por su Id",
            description = "El usuario con rol USER y el usuario ADMIN podrán obtener una tarea por su Id",
            tags = { "Get" })

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TaskDtoSend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") })})
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(taskService.findById(id));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(
            summary = "Todas las tareas de un usuario",
            description = "El usuario con rol USER y el usuario ADMIN podrán obtener todas las tareas de un usuario por su el Id de este",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TaskDtoSend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") })})
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> findAllByUserId(@PathVariable Long userId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try {
            return ResponseEntity.ok(taskService.findByUserId(userId, page, size));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(
            summary = "Guardar una tarea",
            description = "El usuario con rol USER y el usuario ADMIN podrán guardar una tarea",
            tags = { "Post" })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDtoSave.class),
                    examples = @ExampleObject(name = "Ejemplo de como guardar una tarea",
                            value = "{\n" +
                                    "  \"name\": \"Programar Backend\",\n" +
                                    "  \"description\": \"Hay que hacer el Backend\",\n" +
                                    "  \"start_date\": \"12/09/2024\",\n" +
                                    "  \"end_date\": \"13/09/2024\",\n" +
                                    "  \"status\": {\n" +
                                    "    \"name\": \"To do\",\n" +
                                    "    \"description\": \"la tarea esta creada\"\n" +
                                    "  }\n" +
                                    "}",
                            description = "Json con los datos de registro de una tarea")
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = TaskDtoSend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")
            })})
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody TaskDtoSave taskDtoSave,
                                  Principal principal){
        try {
            return new ResponseEntity<>(taskService.save(taskDtoSave, principal.getName()), HttpStatus.CREATED);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Actualizar una tarea",
            description = "El usuario con rol USER y el usuario ADMIN podrán actualizar una tarea",
            tags = { "Put" })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskUpdateDto.class),
                    examples = @ExampleObject(name = "Ejemplo de como actualizar una tarea",
                            value = "{\n" +
                                    "  \"name\": \"hacer algo\",\n" +
                                    "  \"description\": \"hay que hacer algo de la forma tal\",\n" +
                                    "  \"start_date\": \"12/09/2024\",\n" +
                                    "  \"end_date\": \"14/09/2024\"\n" +
                                    "}",
                            description = "Json con los datos de actualización de una tarea")
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = TaskDtoSend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")
            })})
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody TaskUpdateDto taskUpdateDto,
                                    Principal principal){
        try {
            return ResponseEntity.ok(taskService.update(id, taskUpdateDto, principal.getName()));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Borrar una tarea",
            description = "El usuario col rol USER y el usuario ADMIN podrán borrar una tarea por medio de su Id",
            tags = { "Delete" })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.ok("Task deleted");
    }

    @Operation(
            summary = "Borrar todas tarea",
            description = "El usuario con rol ADMIN podrán borrar todas las tareas del sistema",
            tags = { "Delete" })
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAll(){
        taskService.deleteAll();
        return ResponseEntity.ok("All tasks deleted");
    }

    @Operation(
            summary = "Listar tareas por estado",
            description = "Permite a los usuarios con rol USER y ADMIN obtener todas las tareas filtradas por el ID del estado",
            tags = { "Get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TaskDtoSend.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") })
    })
    @GetMapping("/status/{statusId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getTasksByStatusId( @PathVariable Long statusId) {
        try {
            List<TaskDtoSend> tasksByStatus  = taskService.findTasksByStatusId(statusId);
            return ResponseEntity.ok(tasksByStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
