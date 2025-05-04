package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.GymSessionService;
import edu.eci.cvds.ECIBienestarGym.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/gym-sessions")
@Tag(name = "Gym Sessions", description = "Operaciones relacionadas con las sesiones de gimnasio")
public class GymSessionController {

    private final GymSessionService gymSessionService;

    public GymSessionController(GymSessionService gymSessionService){
        this.gymSessionService = gymSessionService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener todas las sesiones de gimnasio")
    public ResponseEntity<ApiResponse<List<GymSession>>> getAllGymSessions() {
        List<GymSession> sessions = gymSessionService.getAllGymSessions();
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio obtenidas", sessions));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener una sesión de gimnasio por su ID")
    public ResponseEntity<ApiResponse<GymSession>> getGymSessionById(
            @Parameter(description = "ID de la sesión", example = "sess123") @PathVariable String id) {
        GymSession session = gymSessionService.getGymSessionById(id);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Sesión de gimnasio no encontrada", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesión de gimnasio encontrada", session));
    }

    @GetMapping("/coach/{coachId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener sesiones de gimnasio por ID de entrenador")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsByCoachId(
            @Parameter(description = "ID del entrenador", example = "coach456") @PathVariable String coachId) {
        User coach = new User();
        coach.setId(coachId);
        List<GymSession> sessions = gymSessionService.getGymSessionsByCoachId(coach);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio del entrenador obtenidas", sessions));
    }

    @GetMapping("/capacity/{capacity}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener sesiones de gimnasio por capacidad")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsByCapacity(
            @Parameter(description = "Capacidad de la sesión", example = "30") @PathVariable int capacity) {
        List<GymSession> sessions = gymSessionService.getGymSessionsByCapacity(capacity);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio con capacidad " + capacity + " obtenidas", sessions));
    }

    @GetMapping("/schedule")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener sesiones de gimnasio por horario")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsBySchedule(
            @Parameter(description = "Fecha y hora en formato ISO (yyyy-MM-ddTHH:mm:ss)", example = "2024-05-03T10:00:00")
            @RequestParam("dateTime") String dateTime) {
        LocalDateTime schedule = LocalDateTime.parse(dateTime);
        List<GymSession> sessions = gymSessionService.getGymSessionsBySchedule(schedule);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio encontradas para la fecha", sessions));
    }
}

