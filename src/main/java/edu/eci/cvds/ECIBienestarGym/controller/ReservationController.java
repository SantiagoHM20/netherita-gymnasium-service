package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.enums.Status;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.Reservation;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
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

    @Operation(summary = "Obtener todas las reservas")
    @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente")
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @Operation(summary = "Obtener reserva por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable String id) {
        return reservationService.getReservationById(id);
    }

    @Operation(summary = "Obtener reservas por ID de usuario")
    @ApiResponse(responseCode = "200", description = "Reservas del usuario encontradas")
    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUserId(@PathVariable User userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @Operation(summary = "Obtener reservas por sesión de gimnasio")
    @ApiResponse(responseCode = "200", description = "Reservas de la sesión encontradas")
    @GetMapping("/session/{sessionId}")
    public List<Reservation> getReservationsByGymSession(@PathVariable String sessionId) {
        GymSession gymSession = new GymSession();
        gymSession.setId(sessionId);
        return reservationService.getReservationsByGymSession(gymSession);
    }

    @Operation(summary = "Obtener reservas por fecha de reserva")
    @ApiResponse(responseCode = "200", description = "Reservas encontradas en la fecha especificada")
    @GetMapping("/date")
    public List<Reservation> getReservationsByReservationDate(
            @RequestParam("datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservationDate) {
        return reservationService.getReservationsByReservationDate(reservationDate);
    }

    @Operation(summary = "Obtener reservas por estado")
    @ApiResponse(responseCode = "200", description = "Reservas encontradas con el estado especificado")
    @GetMapping("/status/{status}")
    public List<Reservation> getReservationsByState(@PathVariable Status status) {
        return reservationService.getReservationsByState(status);
    }
}