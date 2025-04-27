package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.embeddables.Exercise;
import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
import edu.eci.cvds.ECIBienestarGym.model.Routine;
import edu.eci.cvds.ECIBienestarGym.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
@Tag(name = "Routines", description = "Operaciones relacionadas con rutinas de entrenamiento")
public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @Operation(summary = "Obtener todas las rutinas")
    @ApiResponse(responseCode = "200", description = "Rutinas obtenidas exitosamente")
    @GetMapping
    public List<Routine> getAllRoutines() {
        return routineService.getAllRoutines();
    }

    @Operation(summary = "Obtener rutina por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutina encontrada"),
            @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    @GetMapping("/{id}")
    public Routine getRoutineById(@PathVariable String id) {
        return routineService.getRoutineById(id);
    }

    @Operation(summary = "Obtener rutinas por nombre")
    @ApiResponse(responseCode = "200", description = "Rutinas encontradas con el nombre especificado")
    @GetMapping("/name/{name}")
    public List<Routine> getRoutinesByName(@PathVariable String name) {
        return routineService.getRoutinesByName(name);
    }

    @Operation(summary = "Obtener rutinas por dificultad")
    @ApiResponse(responseCode = "200", description = "Rutinas encontradas con el nivel de dificultad especificado")
    @GetMapping("/difficulty/{level}")
    public List<Routine> getRoutinesByDifficulty(@PathVariable DifficultyLevel level) {
        return routineService.getRoutinesByDifficulty(level);
    }

    @Operation(summary = "Obtener rutinas por lista de ejercicios")
    @ApiResponse(responseCode = "200", description = "Rutinas encontradas que contienen los ejercicios especificados")
    @GetMapping("/exercises")
    public List<Routine> getRoutinesByExercises(@RequestBody List<Exercise> exercises) {
        return routineService.getRoutinesByExercises(exercises);
    }
}
