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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para autenticación y generación de tokens")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
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
}
