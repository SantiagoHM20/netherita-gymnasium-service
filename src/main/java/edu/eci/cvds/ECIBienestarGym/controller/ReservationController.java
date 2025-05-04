package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.enums.Status;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.Reservation;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservations", description = "Operaciones relacionadas con reservas del gimnasio")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Obtener todas las reservas", description = "Devuelve una lista con todas las reservas registradas.")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ApiResponse<List<Reservation>>> getAllReservations() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas obtenidas exitosamente", reservationService.getAllReservations()));
    }

    @Operation(summary = "Obtener reserva por ID", description = "Devuelve una reserva específica por su ID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Reservation>> getReservationById(
            @Parameter(description = "ID de la reserva") @PathVariable String id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Reserva no encontrada", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Reserva encontrada", reservation));
    }

    @Operation(summary = "Obtener reservas por ID de usuario", description = "Devuelve las reservas asociadas a un usuario.")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByUserId(
            @Parameter(description = "ID del usuario") @PathVariable User userId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas del usuario encontradas", reservationService.getReservationsByUserId(userId)));
    }

    @Operation(summary = "Obtener reservas por sesión de gimnasio", description = "Devuelve las reservas asociadas a una sesión específica del gimnasio.")
    @GetMapping("/session/{sessionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByGymSession(
            @Parameter(description = "ID de la sesión del gimnasio") @PathVariable String sessionId) {
        GymSession gymSession = new GymSession();
        gymSession.setId(sessionId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas de la sesión encontradas", reservationService.getReservationsByGymSession(gymSession)));
    }

    @Operation(summary = "Obtener reservas por fecha de reserva", description = "Devuelve las reservas que coinciden con una fecha determinada.")
    @GetMapping("/date")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByReservationDate(
            @Parameter(description = "Fecha de la reserva en formato ISO") @RequestParam("datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservationDate) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas en la fecha especificada encontradas", reservationService.getReservationsByReservationDate(reservationDate)));
    }

    @Operation(summary = "Obtener reservas por estado", description = "Devuelve las reservas con un estado específico (por ejemplo: CONFIRMADA, CANCELADA).")
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByState(
            @Parameter(description = "Estado de la reserva") @PathVariable Status status) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas encontradas por estado", reservationService.getReservationsByState(status)));
    }
}
