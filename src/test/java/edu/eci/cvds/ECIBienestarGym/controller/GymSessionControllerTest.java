package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.dto.GymSessionDTO;
import edu.eci.cvds.ECIBienestarGym.service.GymSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GymSessionControllerTest {

    @Mock
    private GymSessionService gymSessionService;

    @InjectMocks
    private GymSessionController gymSessionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGymSessions() {
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionService.getAllGymSessions()).thenReturn(mockSessions);

        ResponseEntity<ApiResponse<List<GymSession>>> response = gymSessionController.getAllGymSessions();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(gymSessionService, times(1)).getAllGymSessions();
    }

    

    @Test
    void getGymSessionsByCoachId() {
        String coachId = "coach456";
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionService.getGymSessionsByCoachId(any())).thenReturn(mockSessions);

        ResponseEntity<ApiResponse<List<GymSession>>> response = gymSessionController.getGymSessionsByCoachId(coachId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(gymSessionService, times(1)).getGymSessionsByCoachId(any());
    }

    @Test
    void getGymSessionsByCapacity() {
        int capacity = 30;
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionService.getGymSessionsByCapacity(capacity)).thenReturn(mockSessions);

        ResponseEntity<ApiResponse<List<GymSession>>> response = gymSessionController.getGymSessionsByCapacity(capacity);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(gymSessionService, times(1)).getGymSessionsByCapacity(capacity);
    }

    @Test
    void getGymSessionsByDate() {
        LocalDate date = LocalDate.now();
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionService.getGymSessionsByDate(any())).thenReturn(mockSessions);

        ResponseEntity<ApiResponse<List<GymSession>>> response = gymSessionController.getGymSessionsByDate(date.atStartOfDay());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(gymSessionService, times(1)).getGymSessionsByDate(any());
    }

    @Test
    void getGymSessionsByDateAndTime() {
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionService.getGymSessionsByDateAndTime(date, startTime, endTime)).thenReturn(mockSessions);

        ResponseEntity<ApiResponse<List<GymSession>>> response = gymSessionController.getGymSessionsByDateAndTime(
                date, startTime.toString(), endTime.toString());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(gymSessionService, times(1)).getGymSessionsByDateAndTime(date, startTime, endTime);
    }

    @Test
    void createGymSession() {
        GymSessionDTO gymSessionDTO = new GymSessionDTO();
        GymSession mockSession = new GymSession();
        when(gymSessionService.createGymSession(gymSessionDTO)).thenReturn(mockSession);

        ResponseEntity<ApiResponse<GymSession>> response = gymSessionController.createGymSession(gymSessionDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockSession, response.getBody().getData());
        verify(gymSessionService, times(1)).createGymSession(gymSessionDTO);
    }

    @Test
    void updateGymSession() throws GYMException {
        String sessionId = "sess123";
        GymSessionDTO gymSessionDTO = new GymSessionDTO();
        GymSession mockSession = new GymSession();
        when(gymSessionService.updateGymSession(sessionId, gymSessionDTO)).thenReturn(mockSession);

        ResponseEntity<ApiResponse<GymSession>> response = gymSessionController.updateGymSession(sessionId, gymSessionDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockSession, response.getBody().getData());
        verify(gymSessionService, times(1)).updateGymSession(sessionId, gymSessionDTO);
    }

    @Test
    void deleteGymSession() throws GYMException {
        String sessionId = "sess123";
        doNothing().when(gymSessionService).deleteGymSession(sessionId);

        ResponseEntity<ApiResponse<Void>> response = gymSessionController.deleteGymSession(sessionId);

        assertEquals(200, response.getStatusCodeValue());
        verify(gymSessionService, times(1)).deleteGymSession(sessionId);
    }

    @Test
    void getGymSessionsByStartTimeAndEndTime() {
        LocalTime startTime = LocalTime.of(10, 0);  // 10:00 AM
        LocalTime endTime = LocalTime.of(12, 0);   // 12:00 PM
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        
        when(gymSessionService.getGymSessionsByStartTimeAndEndTime(startTime, endTime))
                .thenReturn(mockSessions);

        ResponseEntity<ApiResponse<List<GymSession>>> response = 
                gymSessionController.getGymSessionsByStartTimeAndEndTime(startTime.toString(), endTime.toString());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());  // Verificar que se obtuvieron 2 sesiones
        verify(gymSessionService, times(1)).getGymSessionsByStartTimeAndEndTime(startTime, endTime);
    }

    @Test
    void getGymSessionsByEndTime() {
        String endTime = "12:00:00";  // Hora de fin en formato ISO
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        
        when(gymSessionService.getGymSessionsByEndTime(LocalTime.parse(endTime)))
                .thenReturn(mockSessions);

        ResponseEntity<ApiResponse<List<GymSession>>> response = 
                gymSessionController.getGymSessionsByEndTime(endTime);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());  // Verificar que se obtuvieron 2 sesiones
        verify(gymSessionService, times(1)).getGymSessionsByEndTime(LocalTime.parse(endTime));
    }

    @Test
    void getGymSessionById() throws GYMException {
        String sessionId = "sess123";
        GymSession mockSession = new GymSession();
        when(gymSessionService.getGymSessionById(sessionId)).thenReturn(mockSession);

        ResponseEntity<ApiResponse<GymSession>> response = gymSessionController.getGymSessionById(sessionId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockSession, response.getBody().getData());  // Verificar que la sesión coincide con la mockeada
        verify(gymSessionService, times(1)).getGymSessionById(sessionId);
    }

    @Test
    void getGymSessionByIdNotFound() throws GYMException {
        String sessionId = "sess123";
        when(gymSessionService.getGymSessionById(sessionId)).thenReturn(null);

        ResponseEntity<ApiResponse<GymSession>> response = gymSessionController.getGymSessionById(sessionId);

        assertEquals(404, response.getStatusCodeValue());  // Verificar que se devuelve el código 404
        assertEquals("Sesión de gimnasio no encontrada", response.getBody().getMessage());
        verify(gymSessionService, times(1)).getGymSessionById(sessionId);
    }
}