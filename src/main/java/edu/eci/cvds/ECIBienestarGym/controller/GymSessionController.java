package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.GymSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/gym-sessions")
@RequiredArgsConstructor
@Tag(name = "Gym Sessions", description = "Operaciones relacionadas con las sesiones de gimnasio")
public class GymSessionController {

    private final GymSessionService gymSessionService;

    @GetMapping("/")
    @Operation(summary = "Obtener todas las sesiones de gimnasio")
    public List<GymSession> getAllGymSessions() {
        return gymSessionService.getAllGymSessions();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una sesión de gimnasio por su ID")
    public GymSession getGymSessionById(@PathVariable String id) {
        return gymSessionService.getGymSessionById(id);
    }

    @GetMapping("/coach/{coachId}")
    @Operation(summary = "Obtener sesiones de gimnasio por ID de entrenador")
    public List<GymSession> getGymSessionsByCoachId(@PathVariable String coachId) {
        User coach = new User();
        coach.setId(coachId); // O usa tu constructor adecuado si tienes
        return gymSessionService.getGymSessionsByCoachId(coach);
    }

    @GetMapping("/capacity/{capacity}")
    @Operation(summary = "Obtener sesiones de gimnasio por capacidad")
    public List<GymSession> getGymSessionsByCapacity(@PathVariable int capacity) {
        return gymSessionService.getGymSessionsByCapacity(capacity);
    }

    @GetMapping("/schedule")
    @Operation(summary = "Obtener sesiones de gimnasio por horario")
    public List<GymSession> getGymSessionsBySchedule(@RequestParam("dateTime") String dateTime) {
        LocalDateTime schedule = LocalDateTime.parse(dateTime);
        return gymSessionService.getGymSessionsBySchedule(schedule);
    }
}