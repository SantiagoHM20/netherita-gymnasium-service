package edu.eci.cvds.ECIBienestarGym.controller;


import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api")
@Tag(name = "Usuarios", description = "Operaciones para gestionar usuarios del sistema")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/trainer/users")

    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios del sistema.")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios obtenidos exitosamente", userService.getAllUsers()));
    }

    @GetMapping("/user/users/{id}")

    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario en el sistema según su identificador único.")
    public ResponseEntity<ApiResponse<User>> getUserById(
            @Parameter(description = "ID del usuario a buscar", required = true) @PathVariable("id") String id)
            {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario con id: " + id, userService.getUsersById(id)));
    }

    @GetMapping("/trainer/users/name/{name}")

    @Operation(summary = "Obtener usuarios por nombre", description = "Busca usuarios en el sistema según su nombre.")
    public ResponseEntity<ApiResponse<List<User>>> getUsersByName(
            @Parameter(description = "Nombre del usuario a buscar", required = true) @PathVariable String name) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios con nombre: " + name, userService.getUsersByName(name)));
    }

    @GetMapping("/user/users/email")

    @Operation(summary = "Buscar usuario por correo electrónico", description = "Busca un usuario en el sistema según su correo electrónico.")
    public ResponseEntity<ApiResponse<Optional<User>>> getUserByEmail(
            @Parameter(description = "Correo electrónico del usuario a buscar", required = true) @RequestParam String email) {
        Optional<User> user = userService.getUsersByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuario no encontrado", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user));
    }

    @GetMapping("/trainer/users/role/{role}")

    @Operation(summary = "Obtener usuarios por rol", description = "Retorna una lista de usuarios con el rol especificado.")
    public ResponseEntity<ApiResponse<List<User>>> getUsersByRole(
            @Parameter(description = "Rol del usuario a buscar", required = true) @PathVariable Role role) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios con rol: " + role, userService.getUsersByRole(role)));
    }

    @GetMapping("trainer/users/registration-date/{registrationDate}")

    @Operation(summary = "Obtener usuarios por fecha de registro", description = "Retorna una lista de usuarios registrados en la fecha especificada (formato: yyyy-MM-dd).")
    public ResponseEntity<ApiResponse<List<User>>> getUsersByRegistrationDate(
            @Parameter(description = "Fecha de registro en formato yyyy-MM-dd", required = true) @PathVariable String registrationDate) {
        LocalDate date = LocalDate.parse(registrationDate);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios registrados en " + registrationDate, userService.getUsersByRegistrationDate(date)));
    }

    @PostMapping("/users")

    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema.")
    public ResponseEntity<ApiResponse<User>> createUser(
            @Parameter(description = "Datos del usuario a crear", required = true) @RequestBody UserDTO userDTO) throws GYMException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Usuario creado", userService.createUser(userDTO)));
    }

    @PutMapping("/users/{id}")

    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente en el sistema.")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @Parameter(description = "ID del usuario a actualizar", required = true) @PathVariable("id") String id,
            @Parameter(description = "Datos actualizados del usuario", required = true) @RequestBody UserDTO userDTO) throws GYMException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado", userService.updateUser(id, userDTO)));
    }

    @DeleteMapping("/users/{id}")

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema.")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable("id") String id) throws GYMException {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario eliminado", null));
    }
}
