package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.RoutineDTO;
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
    void getRoutineById() {
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
    void updateRoutine() {
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
    void deleteRoutine() {
        String id = "routine123";
        doNothing().when(routineService).deleteRoutine(id);

        ResponseEntity<ApiResponse<Void>> response = routineController.deleteRoutine(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(routineService, times(1)).deleteRoutine(id);
    }
}