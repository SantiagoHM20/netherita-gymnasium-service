package edu.eci.cvds.ECIBienestarGym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ECIBienestarGym.dto.AuthRequest;
import edu.eci.cvds.ECIBienestarGym.dto.AuthResponse;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.service.AuthService;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
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
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }
        // Validar rol
        if (user.getRole() == null ||
                (!user.getRole().equals(STUDENT) && !user.getRole().equals(TRAINER))) {
            return ResponseEntity.badRequest().body("El rol debe ser STUDENT o TRAINER.");
        }
        User savedUser = userRepository.save(user);

        // Generar token JWT para el usuario registrado
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(savedUser.getEmail());
        authRequest.setRole(savedUser.getRole());
        try {
            AuthResponse response = authService.authenticate(authRequest);
            return ResponseEntity.ok(response);
        } catch (GYMException e) {
            return ResponseEntity.status(401).body("No se pudo autenticar el usuario recién registrado.");
        }
    }
}
