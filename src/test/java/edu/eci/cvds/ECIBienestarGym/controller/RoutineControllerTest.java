package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.RoutineDTO;
import edu.eci.cvds.ECIBienestarGym.embeddables.Exercise;
import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.Routine;
import edu.eci.cvds.ECIBienestarGym.service.RoutineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoutineControllerTest {

    @Mock
    private RoutineService routineService;

    @InjectMocks
    private RoutineController routineController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRoutines() {
        List<Routine> mockRoutines = Arrays.asList(new Routine(), new Routine());
        when(routineService.getAllRoutines()).thenReturn(mockRoutines);

        ResponseEntity<ApiResponse<List<Routine>>> response = routineController.getAllRoutines();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(routineService, times(1)).getAllRoutines();
    }

    @Test
    void getRoutineById() throws GYMException {
        String id = "routine123";
        Routine mockRoutine = new Routine();
        when(routineService.getRoutineById(id)).thenReturn(mockRoutine);

        ResponseEntity<ApiResponse<Routine>> response = routineController.getRoutineById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockRoutine, response.getBody().getData());
        verify(routineService, times(1)).getRoutineById(id);
    }

    @Test
    void createRoutine() {
        RoutineDTO routineDTO = new RoutineDTO();
        Routine mockRoutine = new Routine();
        when(routineService.createRoutine(routineDTO)).thenReturn(mockRoutine);

        ResponseEntity<ApiResponse<Routine>> response = routineController.createRoutine(routineDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockRoutine, response.getBody().getData());
        verify(routineService, times(1)).createRoutine(routineDTO);
    }

    @Test
    void updateRoutine() throws GYMException {
        String id = "routine123";
        RoutineDTO routineDTO = new RoutineDTO();
        Routine mockRoutine = new Routine();
        when(routineService.updateRoutine(id, routineDTO)).thenReturn(mockRoutine);

        ResponseEntity<ApiResponse<Routine>> response = routineController.updateRoutine(id, routineDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockRoutine, response.getBody().getData());
        verify(routineService, times(1)).updateRoutine(id, routineDTO);
    }

    @Test
    void deleteRoutine() throws GYMException {
        String id = "routine123";
        doNothing().when(routineService).deleteRoutine(id);

        ResponseEntity<ApiResponse<Void>> response = routineController.deleteRoutine(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(routineService, times(1)).deleteRoutine(id);
    }

    @Test
    void getRoutineById_NotFound() throws GYMException {
        String id = "nonexistent123";
        when(routineService.getRoutineById(id)).thenReturn(null);

        ResponseEntity<ApiResponse<Routine>> response = routineController.getRoutineById(id);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(false, response.getBody().isSuccess());
        assertEquals("Rutina no encontrada", response.getBody().getMessage());
        verify(routineService, times(1)).getRoutineById(id);
    }

    @Test
    void createRoutine_InvalidData() {
        RoutineDTO routineDTO = new RoutineDTO(); // Puede tener datos inválidos.
        when(routineService.createRoutine(routineDTO)).thenThrow(new IllegalArgumentException("Datos inválidos"));

        try {
            routineController.createRoutine(routineDTO);
        } catch (IllegalArgumentException e) {
            assertEquals("Datos inválidos", e.getMessage());
        }
    }

    @Test
    void getRoutinesByName() {
        String name = "Cardio Routine";
        List<Routine> mockRoutines = Arrays.asList(new Routine(), new Routine());
        when(routineService.getRoutinesByName(name)).thenReturn(mockRoutines);

        ResponseEntity<ApiResponse<List<Routine>>> response = routineController.getRoutinesByName(name);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(routineService, times(1)).getRoutinesByName(name);
    }

    @Test
    void getRoutinesByName_NotFound() {
        String name = "Nonexistent Routine";
        when(routineService.getRoutinesByName(name)).thenReturn(Arrays.asList());

        ResponseEntity<ApiResponse<List<Routine>>> response = routineController.getRoutinesByName(name);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().getData().size());
        verify(routineService, times(1)).getRoutinesByName(name);
    }

    @Test
    void getRoutinesByDifficulty() {
        DifficultyLevel level = DifficultyLevel.HARD;
        List<Routine> mockRoutines = Arrays.asList(new Routine(), new Routine());
        when(routineService.getRoutinesByDifficulty(level)).thenReturn(mockRoutines);

        ResponseEntity<ApiResponse<List<Routine>>> response = routineController.getRoutinesByDifficulty(level);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(routineService, times(1)).getRoutinesByDifficulty(level);
    }

    @Test
    void getRoutinesByDifficulty_NotFound() {
        DifficultyLevel level = DifficultyLevel.EASY;
        when(routineService.getRoutinesByDifficulty(level)).thenReturn(Arrays.asList());

        ResponseEntity<ApiResponse<List<Routine>>> response = routineController.getRoutinesByDifficulty(level);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().getData().size());
        verify(routineService, times(1)).getRoutinesByDifficulty(level);
    }

    @Test
    void getRoutinesByExercises() {
        List<Exercise> exercises = Arrays.asList(new Exercise(), new Exercise());
        List<Routine> mockRoutines = Arrays.asList(new Routine(), new Routine());
        when(routineService.getRoutinesByExercises(exercises)).thenReturn(mockRoutines);

        ResponseEntity<ApiResponse<List<Routine>>> response = routineController.getRoutinesByExercises(exercises);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(routineService, times(1)).getRoutinesByExercises(exercises);
    }

    @Test
    void getRoutinesByExercises_NotFound() {
        List<Exercise> exercises = Arrays.asList(new Exercise(), new Exercise());
        when(routineService.getRoutinesByExercises(exercises)).thenReturn(Arrays.asList());

        ResponseEntity<ApiResponse<List<Routine>>> response = routineController.getRoutinesByExercises(exercises);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().getData().size());
        verify(routineService, times(1)).getRoutinesByExercises(exercises);
    }

}