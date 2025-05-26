package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.PhysicalProgressDTO;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.PhysicalProgress;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.PhysicalProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PhysicalProgressControllerTest {

    @Mock
    private PhysicalProgressService physicalProgressService;

    @InjectMocks
    private PhysicalProgressController physicalProgressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllPhysicalProgressWhenGetAllIsCalled() {
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());
        when(physicalProgressService.getAllPhysicalProgress()).thenReturn(mockProgress);

        ResponseEntity<ApiResponse<List<PhysicalProgress>>> response = physicalProgressController.getAllPhysicalProgress();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(physicalProgressService, times(1)).getAllPhysicalProgress();
    }

    @Test
    void shouldReturnPhysicalProgressByUserIdAndDateWhenValidParamsAreGiven() {
        String userId = "user123";
        String registrationDate = "2024-05-01";
        LocalDate date = LocalDate.parse(registrationDate);
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());

        when(physicalProgressService.getPhysicalProgressByUserIdAndDate(any(User.class), eq(date))).thenReturn(mockProgress);

        ResponseEntity<ApiResponse<List<PhysicalProgress>>> response = physicalProgressController.getPhysicalProgressByUserIdAndDate(userId, registrationDate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(physicalProgressService, times(1)).getPhysicalProgressByUserIdAndDate(any(User.class), eq(date));
    }

    @Test
    void shouldReturnPhysicalProgressByIdWhenExists() throws GYMException {
        String id = "progress123";
        PhysicalProgress mockProgress = new PhysicalProgress();
        when(physicalProgressService.getPhysicalProgressById(id)).thenReturn(mockProgress);

        ResponseEntity<ApiResponse<PhysicalProgress>> response = physicalProgressController.getPhysicalProgressById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockProgress, response.getBody().getData());
        verify(physicalProgressService, times(1)).getPhysicalProgressById(id);
    }

    @Test
    void shouldReturnPhysicalProgressByUserIdAndDateRangeWhenValidParamsAreGiven() {
        String userId = "user123";
        String startDate = "2024-05-01";
        String endDate = "2024-05-31";
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());

        when(physicalProgressService.getPhysicalProgressByUserIdAndDateBetween(any(User.class), eq(start), eq(end))).thenReturn(mockProgress);

        ResponseEntity<ApiResponse<List<PhysicalProgress>>> response = physicalProgressController.getPhysicalProgressByUserIdAndDateRange(userId, startDate, endDate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(physicalProgressService, times(1)).getPhysicalProgressByUserIdAndDateBetween(any(User.class), eq(start), eq(end));
    }


    @Test
    void shouldReturnPhysicalProgressByUserIdWhenValidUserIdIsGiven() {
        String userId = "user123";
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());
        when(physicalProgressService.getPhysicalProgressByUserId(any(User.class))).thenReturn(mockProgress);

        ResponseEntity<ApiResponse<List<PhysicalProgress>>> response = physicalProgressController.getPhysicalProgressByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(physicalProgressService, times(1)).getPhysicalProgressByUserId(any(User.class));
    }

    @Test
    void shouldReturnNotFoundWhenPhysicalProgressIsNull() throws GYMException {
        String id = "someId";

        // Simula que el servicio retorna null (aunque la lógica real lanza excepción)
        when(physicalProgressService.getPhysicalProgressById(id)).thenReturn(null);

        ResponseEntity<ApiResponse<PhysicalProgress>> response = physicalProgressController.getPhysicalProgressById(id);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(false, response.getBody().isSuccess());
        assertEquals("Registro de progreso físico no encontrado", response.getBody().getMessage());
        assertEquals(null, response.getBody().getData());

        verify(physicalProgressService, times(1)).getPhysicalProgressById(id);
    }

    @Test
    void shouldReturnPhysicalProgressByRegistrationDateWhenValidDateIsGiven() {
        String registrationDate = "2024-05-01";
        LocalDate date = LocalDate.parse(registrationDate);
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());
        when(physicalProgressService.getPhysicalProgressByRegistrationDate(date)).thenReturn(mockProgress);

        ResponseEntity<ApiResponse<List<PhysicalProgress>>> response = physicalProgressController.getPhysicalProgressByRegistrationDate(registrationDate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(physicalProgressService, times(1)).getPhysicalProgressByRegistrationDate(date);
    }

    @Test
    void shouldCreatePhysicalProgressWhenValidDTOIsProvided() {
        PhysicalProgressDTO progressDTO = new PhysicalProgressDTO();
        PhysicalProgress mockProgress = new PhysicalProgress();
        when(physicalProgressService.createPhysicalProgress(progressDTO)).thenReturn(mockProgress);

        ResponseEntity<ApiResponse<PhysicalProgress>> response = physicalProgressController.createPhysicalProgress(progressDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockProgress, response.getBody().getData());
        verify(physicalProgressService, times(1)).createPhysicalProgress(progressDTO);
    }
}
