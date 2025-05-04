package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.RoutineDTO;
import edu.eci.cvds.ECIBienestarGym.embeddables.Exercise;
import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.Routine;
import edu.eci.cvds.ECIBienestarGym.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Obtener todas las rutinas", description = "Devuelve una lista con todas las rutinas registradas.")

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Routine>>> getAllRoutines() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Rutinas obtenidas exitosamente", routineService.getAllRoutines()));
    }

    @Operation(summary = "Obtener rutina por ID", description = "Devuelve la rutina correspondiente al ID proporcionado.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Routine>> getRoutineById(
            @Parameter(description = "ID de la rutina a consultar", required = true) @PathVariable String id) {
        Routine routine = routineService.getRoutineById(id);
        if (routine == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Rutina no encontrada", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Rutina encontrada", routine));
    }

    @Operation(summary = "Obtener rutinas por nombre", description = "Devuelve las rutinas que coinciden con el nombre proporcionado.")
    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Routine>>> getRoutinesByName(
            @Parameter(description = "Nombre de la rutina") @PathVariable String name) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Rutinas encontradas", routineService.getRoutinesByName(name)));
    }

    @Operation(summary = "Obtener rutinas por dificultad", description = "Devuelve las rutinas que tienen el nivel de dificultad especificado.")
    @GetMapping("/difficulty/{level}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Routine>>> getRoutinesByDifficulty(
            @Parameter(description = "Nivel de dificultad de la rutina") @PathVariable DifficultyLevel level) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Rutinas encontradas", routineService.getRoutinesByDifficulty(level)));
    }

    @Operation(summary = "Obtener rutinas por lista de ejercicios", description = "Devuelve las rutinas que contienen los ejercicios especificados.")
    @GetMapping("/exercises")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Routine>>> getRoutinesByExercises(
            @Parameter(description = "Lista de ejercicios") @RequestBody List<Exercise> exercises) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Rutinas encontradas", routineService.getRoutinesByExercises(exercises)));
    }

    @Operation(summary = "Crear rutina", description = "Crea una nueva rutina de entrenamiento.")
    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Routine>> createRoutine(@RequestBody RoutineDTO routine) {
        Routine createdRoutine = routineService.createRoutine(routine);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Rutina creada exitosamente", createdRoutine));
    }

    @Operation(summary = "Actualizar rutina", description = "Actualiza una rutina existente.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Routine>> updateRoutine(
            @Parameter(description = "ID de la rutina a actualizar", required = true) @PathVariable String id,
            @RequestBody RoutineDTO routine) {
        Routine updatedRoutine = routineService.updateRoutine(id, routine);
        return ResponseEntity.ok(new ApiResponse<>(true, "Rutina actualizada exitosamente", updatedRoutine));
    }

    @Operation(summary = "Eliminar rutina", description = "Elimina una rutina existente.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteRoutine(
            @Parameter(description = "ID de la rutina a eliminar", required = true) @PathVariable String id) {
        routineService.deleteRoutine(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Rutina eliminada exitosamente", null));
    }
}
