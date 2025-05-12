package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.ReservationDTO;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.Reservation;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.ReservationService;
import edu.eci.cvds.ECIBienestarGym.enums.Status;
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
import static org.mockito.Mockito.*;

public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllReservationsWhenGetAllReservations() {
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationService.getAllReservations()).thenReturn(mockReservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getAllReservations();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void shouldReturnReservationByIdWhenGetReservationById() throws GYMException {
        String id = "reservation123";
        Reservation mockReservation = new Reservation();
        when(reservationService.getReservationById(id)).thenReturn(mockReservation);

        ResponseEntity<ApiResponse<Reservation>> response = reservationController.getReservationById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockReservation, response.getBody().getData());
        verify(reservationService, times(1)).getReservationById(id);
    }

    @Test
    void shouldReturnReservationsByUserIdWhenGetReservationsByUserId() {
        User user = new User(); 
        user.setId("user123"); 
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationService.getReservationsByUserId(user)).thenReturn(mockReservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByUserId("user123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByUserId(user);
    }

    @Test
    void shouldReturnReservationsByDateWhenGetReservationsByDate() {
        String date = "2024-05-01";
        LocalDate localDate = LocalDate.parse(date);
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationService.getReservationsByReservationDate(localDate.atStartOfDay())).thenReturn(mockReservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByReservationDate(localDate.atStartOfDay());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByReservationDate(localDate.atStartOfDay());
    }

    @Test
    void shouldCreateReservationWhenCreateReservation() {
        ReservationDTO reservationDTO = new ReservationDTO();
        Reservation mockReservation = new Reservation();
        when(reservationService.createReservation(reservationDTO)).thenReturn(mockReservation);

        ResponseEntity<ApiResponse<Reservation>> response = reservationController.createReservation(reservationDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockReservation, response.getBody().getData());
        verify(reservationService, times(1)).createReservation(reservationDTO);
    }

    @Test
    void shouldUpdateReservationWhenUpdateReservation() throws GYMException {
        String id = "reservation123";
        ReservationDTO reservationDTO = new ReservationDTO();
        Reservation mockReservation = new Reservation();
        when(reservationService.updateReservation(id, reservationDTO)).thenReturn(mockReservation);

        ResponseEntity<ApiResponse<Reservation>> response = reservationController.updateReservation(id, reservationDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockReservation, response.getBody().getData());
        verify(reservationService, times(1)).updateReservation(id, reservationDTO);
    }

    @Test
    void shouldDeleteReservationWhenDeleteReservation() throws GYMException {
        String id = "reservation123";
        doNothing().when(reservationService).deleteReservation(id);

        ResponseEntity<ApiResponse<Void>> response = reservationController.deleteReservation(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(reservationService, times(1)).deleteReservation(id);
    }

    @Test
    void shouldReturnNotFoundWhenGetReservationByIdNotFound() throws GYMException {
        String id = "nonExistentReservationId";
        when(reservationService.getReservationById(id)).thenReturn(null);

        ResponseEntity<ApiResponse<Reservation>> response = reservationController.getReservationById(id);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Reserva no encontrada", response.getBody().getMessage());
        verify(reservationService, times(1)).getReservationById(id);
    }

    @Test
    void shouldReturnReservationsByGymSessionWhenGetReservationsByGymSession() {
        String sessionId = "session123";
        GymSession gymSession = new GymSession();
        gymSession.setId(sessionId);
        
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationService.getReservationsByGymSession(gymSession)).thenReturn(mockReservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByGymSession(sessionId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        assertEquals("Reservas de la sesión encontradas", response.getBody().getMessage());
        verify(reservationService, times(1)).getReservationsByGymSession(gymSession);
    }

    @Test
    void shouldReturnReservationsByStateWhenGetReservationsByState() {
        Status status = Status.APPROVED;
        List<Reservation> mockReservations = Arrays.asList(new Reservation());
        when(reservationService.getReservationsByState(status)).thenReturn(mockReservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByState(status);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Reservas encontradas por estado", response.getBody().getMessage());
        verify(reservationService, times(1)).getReservationsByState(status);
    }
}
