package com.taskmanager.controllers;

import com.taskmanager.models.dtos.auth.LoginDto;
import com.taskmanager.models.dtos.auth.ResponseAuth;
import com.taskmanager.models.dtos.save.UserDtoSave;
import com.taskmanager.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticación y registro", description = "Recurso para loguearse y registrarse en el sistema")
@RestController
@RequestMapping("/AppTask/V1.0.0/auth")
@AllArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Iniciar sesion",
            description = "Iniciar sesión en la aplicación",
            tags = { "Post" })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginDto.class),
                    examples = @ExampleObject(name = "Ejemplo de inicio de sesión",
                            value = "{\n" +
                                    "  \"email\": \"usuario@ejemplo.com\",\n" +
                                    "  \"password\": \"password123\"\n" +
                                    "}",
                            description = "Json con los datos de inicio de sesión")
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseAuth.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") })})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto login) {
        try {
            return ResponseEntity.ok(authService.login(login));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(
            summary = "Registro de usuario",
            description = "Registrasrse en la aplicación",
            tags = { "Post" })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDtoSave.class),
                    examples = @ExampleObject(name = "Ejemplo de registro",
                            value = "{\n" +
                                    "  \"userName\": \"usuario123\",\n" +
                                    "  \"email\": \"usuario@ejemplo.com\",\n" +
                                    "  \"password\": \"password123\",\n" +
                                    "  \"role\": [\"USER\"]\n" +
                                    "}",
                            description = "Json con los datos de registro")
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") })})
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDtoSave userDtoSave) {
        try {
            return new ResponseEntity<>(authService.register(userDtoSave), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
