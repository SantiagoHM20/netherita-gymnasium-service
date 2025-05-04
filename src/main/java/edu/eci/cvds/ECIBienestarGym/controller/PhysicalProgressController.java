package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.PhysicalProgress;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.PhysicalProgressService;
import edu.eci.cvds.ECIBienestarGym.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/physical-progress")
@Tag(name = "Physical Progress", description = "Operaciones relacionadas con el progreso físico")
public class PhysicalProgressController {

    private final PhysicalProgressService physicalProgressService;

    public PhysicalProgressController(PhysicalProgressService physicalProgressService) {
        this.physicalProgressService = physicalProgressService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener todos los registros de progreso físico")
    public ResponseEntity<ApiResponse<List<PhysicalProgress>>> getAllPhysicalProgress() {
        List<PhysicalProgress> progressList = physicalProgressService.getAllPhysicalProgress();
        return ResponseEntity.ok(new ApiResponse<>(true, "Registros de progreso físico obtenidos", progressList));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener un registro de progreso físico por su ID")
    public ResponseEntity<ApiResponse<PhysicalProgress>> getPhysicalProgressById(
            @Parameter(description = "ID del registro", example = "abc123") @PathVariable String id) {
        PhysicalProgress progress = physicalProgressService.getPhysicalProgressById(id);
        if (progress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Registro de progreso físico no encontrado", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Registro de progreso físico encontrado", progress));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener registros de progreso físico por ID de usuario")
    public ResponseEntity<ApiResponse<List<PhysicalProgress>>> getPhysicalProgressByUserId(
            @Parameter(description = "ID del usuario", example = "user123") @PathVariable String userId) {
        User user = new User();
        user.setId(userId);
        List<PhysicalProgress> progressList = physicalProgressService.getPhysicalProgressByUserId(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registros de progreso físico del usuario obtenidos", progressList));
    }

    @GetMapping("/date")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener registros de progreso físico por fecha de registro")
    public ResponseEntity<ApiResponse<List<PhysicalProgress>>> getPhysicalProgressByRegistrationDate(
            @Parameter(description = "Fecha en formato ISO (yyyy-MM-dd)", example = "2024-05-01")
            @RequestParam("registrationDate") String registrationDate) {
        LocalDate date = LocalDate.parse(registrationDate);
        List<PhysicalProgress> progressList = physicalProgressService.getPhysicalProgressByRegistrationDate(date);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registros de progreso físico encontrados por fecha", progressList));
    }
}
