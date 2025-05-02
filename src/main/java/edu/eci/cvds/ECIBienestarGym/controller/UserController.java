package edu.eci.cvds.ECIBienestarGym.controller;


import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Operaciones para gestionar usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


     @PostMapping
     @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en el sistema")
     public User createUser(@RequestBody User user) {
     return userService.createUser(user);
     }


    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista con todos los usuarios registrados")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Obtener usuarios por nombre", description = "Retorna una lista de usuarios que coinciden con el nombre proporcionado")
    public List<User> getUsersByName(
            @Parameter(description = "Nombre del usuario a buscar") @PathVariable String name) {
        return userService.getUsersByName(name);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Obtener usuarios por email", description = "Retorna una lista de usuarios que coinciden con el email proporcionado")
    public List<User> getUsersByEmail(
            @Parameter(description = "Email del usuario a buscar") @PathVariable String email) {
        return userService.getUsersByEmail(email);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Obtener usuarios por rol", description = "Retorna una lista de usuarios con el rol especificado")
    public List<User> getUsersByRole(
            @Parameter(description = "Rol del usuario a buscar") @PathVariable Role role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/registration-date/{registrationDate}")
    @Operation(summary = "Obtener usuarios por fecha de registro", description = "Retorna una lista de usuarios registrados en la fecha especificada (formato: yyyy-MM-dd)")
    public List<User> getUsersByRegistrationDate(
            @Parameter(description = "Fecha de registro del usuario en formato yyyy-MM-dd") @PathVariable String registrationDate) {
        LocalDate date = LocalDate.parse(registrationDate);
        return userService.getUsersByRegistrationDate(date);
    }
}
