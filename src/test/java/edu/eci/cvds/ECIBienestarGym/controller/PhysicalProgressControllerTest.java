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
