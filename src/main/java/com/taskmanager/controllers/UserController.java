package com.taskmanager.controllers;

import com.taskmanager.models.dtos.send.UserDtoSend;
import com.taskmanager.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Usuarios", description = "Recurso para gestionar los usuarios en el sistema")
@RestController
@RequestMapping("/AppTask/V1.0.0/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Actualizar rol",
            description = "El usuario con rol ADMIN podrán actualizar el rol de un usuario",
            tags = { "Patch" })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(name = "Ejemplo de como actualizar el rol de un usuario",
                            value = "ADMIN",
                            description = "String con el rol a actualizar")
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json")
            })})
    @PatchMapping("/role/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateRole(@PathVariable String email,
                                             @RequestBody String role,
                                             Principal principal) {
        try {
            return ResponseEntity.ok(userService.updateRole(email, role, principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(
            summary = "Obtener todos los usuarios",
            description = "El usuario con rol ADMIN podrán todos los usaurios",
            tags = { "Patch" })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class)
            ))
    @ApiResponses({ @ApiResponse(responseCode = "200")})
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDtoSend>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
            return ResponseEntity.ok(userService.getAllUsers(page, size));
    }
}
