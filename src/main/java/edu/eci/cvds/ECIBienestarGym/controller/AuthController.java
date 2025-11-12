package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ECIBienestarGym.dto.AuthRequest;
import edu.eci.cvds.ECIBienestarGym.dto.AuthResponse;
import edu.eci.cvds.ECIBienestarGym.dto.UserRegistrationDTO;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.service.AuthService;
import edu.eci.cvds.ECIBienestarGym.service.UserService;
import edu.eci.cvds.ECIBienestarGym.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import static edu.eci.cvds.ECIBienestarGym.enums.Role.STUDENT;
import static edu.eci.cvds.ECIBienestarGym.enums.Role.TRAINER;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para autenticación y generación de tokens")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/token")
    @Operation(
            summary = "Generar token JWT",
            description = "Genera un token JWT basado en el correo electrónico y el rol del usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token generado con éxito"),
                    @ApiResponse(responseCode = "401", description = "Usuario no encontrado o no validado")
            }
    )
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse response = authService.authenticate(authRequest);
            return ResponseEntity.ok(response);
        } catch (GYMException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register")
    @Operation(
            summary = "Registrar usuario",
            description = "Registra un nuevo usuario en el sistema como STUDENT o TRAINER y retorna el token JWT",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito"),
                    @ApiResponse(responseCode = "400", description = "El correo ya está registrado o el rol es inválido")
            }
    )
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        // Validar que el correo no esté registrado
        if (userService.getUsersByEmail(registrationDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }

        // Validar rol
        if (registrationDTO.getRole() == null ||
                (!registrationDTO.getRole().equals(STUDENT) && !registrationDTO.getRole().equals(TRAINER))) {
            return ResponseEntity.badRequest().body("El rol debe ser STUDENT o TRAINER.");
        }

        try {
            // Convertir DTO a entidad de forma controlada
            User savedUser = userService.createUser(convertToUserDTO(registrationDTO));

            // Generar token JWT para el usuario registrado
            AuthRequest authRequest = new AuthRequest();
            authRequest.setEmail(savedUser.getEmail());
            authRequest.setRole(savedUser.getRole());

            AuthResponse response = authService.authenticate(authRequest);
            return ResponseEntity.ok(response);
        } catch (GYMException e) {
            return ResponseEntity.badRequest().body("Error al crear el usuario: " + e.getMessage());
        }
    }

    private UserDTO convertToUserDTO(UserRegistrationDTO registrationDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(registrationDTO.getName());
        userDTO.setEmail(registrationDTO.getEmail());
        userDTO.setRole(registrationDTO.getRole());
        userDTO.setGender(registrationDTO.getGender());
        userDTO.setPassword(registrationDTO.getPassword());
        // Campos como id, registrationDate, registered se controlan en el servicio
        return userDTO;
    }
}