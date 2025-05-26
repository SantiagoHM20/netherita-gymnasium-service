package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.GymSessionDTO;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.GymSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Gym Sessions", description = "Operaciones relacionadas con las sesiones de gimnasio")
public class GymSessionController {

    private final GymSessionService gymSessionService;

    public GymSessionController(GymSessionService gymSessionService){
        this.gymSessionService = gymSessionService;
    }

    @GetMapping("/user/session")
    @Operation(summary = "Obtener todas las sesiones de gimnasio")
    public ResponseEntity<ApiResponse<List<GymSession>>> getAllGymSessions() {
        List<GymSession> sessions = gymSessionService.getAllGymSessions();
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio obtenidas", sessions));
    }

    @GetMapping("/trainer/session/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER')")
    @Operation(summary = "Obtener una sesión de gimnasio por su ID")
    public ResponseEntity<ApiResponse<GymSession>> getGymSessionById(
            @Parameter(description = "ID de la sesión", example = "sess123") @PathVariable String id) throws GYMException {
        GymSession session = gymSessionService.getGymSessionById(id);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Sesión de gimnasio no encontrada", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesión de gimnasio encontrada", session));
    }

    @GetMapping("/trainer/session/{coachId}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER')")
    @Operation(summary = "Obtener sesiones de gimnasio por ID de TRAINER")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsByCoachId(
            @Parameter(description = "ID del TRAINER", example = "coach456") @PathVariable String coachId) {
        User coach = new User();
        coach.setId(coachId);
        List<GymSession> sessions = gymSessionService.getGymSessionsByCoachId(coach);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio del TRAINER obtenidas", sessions));
    }

    @GetMapping("/trainer/session/capacity/{capacity}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER')")
    @Operation(summary = "Obtener sesiones de gimnasio por capacidad")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsByCapacity(
            @Parameter(description = "Capacidad de la sesión", example = "30") @PathVariable int capacity) {
        List<GymSession> sessions = gymSessionService.getGymSessionsByCapacity(capacity);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio con capacidad " + capacity + " obtenidas", sessions));
    }

    @GetMapping("/user/session/date/{date}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER', 'STUDENT')")
    @Operation(summary = "Obtener sesiones de gimnasio por fecha")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsByDate(
            @Parameter(description = "Fecha de la sesión", example = "2023-10-01T10:00:00") @PathVariable LocalDate date) {
        List<GymSession> sessions = gymSessionService.getGymSessionsByDate(date);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio en la fecha " + date + " obtenidas", sessions));
    }

    @GetMapping("/user/session/date/{date}/time-range")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER', 'STUDENT')")
    @Operation(summary = "Obtener sesiones de gimnasio por fecha y rango de tiempo")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsByDateAndTime(
            @Parameter(description = "Fecha de la sesión", example = "2023-10-01") @PathVariable LocalDate date,
            @Parameter(description = "Hora de inicio en formato ISO (HH:mm:ss)", example = "10:00:00") @RequestParam("startTime") String startTime,
            @Parameter(description = "Hora de fin en formato ISO (HH:mm:ss)", example = "12:00:00") @RequestParam("endTime") String endTime) {
        LocalTime startTimeParsed = LocalTime.parse(startTime);
        LocalTime endTimeParsed = LocalTime.parse(endTime);
        List<GymSession> sessions = gymSessionService.getGymSessionsByDateAndTime(date, startTimeParsed, endTimeParsed);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio obtenidas por fecha y rango de tiempo", sessions));
    }

    @GetMapping("/user/session/time-range")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER', 'STUDENT')")
    @Operation(summary = "Obtener sesiones de gimnasio por rango de tiempo")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsByStartTimeAndEndTime(
            @Parameter(description = "Hora de inicio en formato ISO (HH:mm:ss)", example = "10:00:00") @RequestParam("startTime") String startTime,
            @Parameter(description = "Hora de fin en formato ISO (HH:mm:ss)", example = "12:00:00") @RequestParam("endTime") String endTime) {
        List<GymSession> sessions = gymSessionService.getGymSessionsByStartTimeAndEndTime(
                LocalTime.parse(startTime), LocalTime.parse(endTime));
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio obtenidas por rango de tiempo", sessions));
    }

    @GetMapping("/user/session/end-time/{endTime}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER', 'STUDENT')")
    @Operation(summary = "Obtener sesiones de gimnasio por hora de fin")
    public ResponseEntity<ApiResponse<List<GymSession>>> getGymSessionsByEndTime(
            @Parameter(description = "Hora de fin en formato ISO (HH:mm:ss)", example = "12:00:00") @PathVariable String endTime) {
        List<GymSession> sessions = gymSessionService.getGymSessionsByEndTime(LocalTime.parse(endTime));
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesiones de gimnasio obtenidas por hora de fin", sessions));
    }

    @PostMapping("/trainer/session")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER')")
    @Operation(summary = "Crear una nueva sesión de gimnasio")
    public ResponseEntity<ApiResponse<GymSession>> createGymSession(
            @Parameter(description = "Detalles de la sesión de gimnasio") @RequestBody GymSessionDTO gymSession) {
        GymSession createdSession = gymSessionService.createGymSession(gymSession);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Sesión de gimnasio creada", createdSession));
    }

    @PutMapping("/trainer/session/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER')")
    @Operation(summary = "Actualizar una sesión de gimnasio")
    public ResponseEntity<ApiResponse<GymSession>> updateGymSession(
            @Parameter(description = "ID de la sesión", example = "sess123") @PathVariable String id,
            @Parameter(description = "Detalles actualizados de la sesión de gimnasio") @RequestBody GymSessionDTO gymSession) throws GYMException {
        GymSession updatedSession = gymSessionService.updateGymSession(id, gymSession);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesión de gimnasio actualizada", updatedSession));
    }

    @PutMapping("/trainer/session/{id}/attendance")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER')")
    @Operation(summary = "Actualizar asistencia de una sesión de gimnasio")
    public ResponseEntity<ApiResponse<GymSession>> updateAttendance(
            @Parameter(description = "ID de la sesión", example = "sess123") @PathVariable String id,
            @Parameter(description = "Lista de asistencia") @RequestBody List<Boolean> attendance) throws GYMException {
        GymSession updatedSession = gymSessionService.updatedAttendance(id, attendance);
        return ResponseEntity.ok(new ApiResponse<>(true, "Asistencia actualizada", updatedSession));
    }

    @DeleteMapping("/trainer/session/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'TRAINER')")
    @Operation(summary = "Eliminar una sesión de gimnasio")
    public ResponseEntity<ApiResponse<Void>> deleteGymSession(
            @Parameter(description = "ID de la sesión", example = "sess123") @PathVariable String id) throws GYMException {
        gymSessionService.deleteGymSession(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesión de gimnasio eliminada", null));
    }
}

