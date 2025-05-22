package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.model.*;
import edu.eci.cvds.ECIBienestarGym.service.GymSessionService;
import edu.eci.cvds.ECIBienestarGym.service.PhysicalProgressService;
import edu.eci.cvds.ECIBienestarGym.service.ReservationService;
import edu.eci.cvds.ECIBienestarGym.statistic.stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

public class StatisticsControllerTest {

    @InjectMocks
    private StatisticsController controller;

    @Mock
    private GymSessionService gymSessionService;

    @Mock
    private ReservationService reservationService;

    @Mock
    private PhysicalProgressService physicalProgressService;

    @Mock
    private stats estadisticasClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSessionStatisticsByTrainer() {
        User coach = new User();
        coach.setId("coach1");

        GymSession session = new GymSession();
        session.setCoachId(coach);

        when(gymSessionService.getAllGymSessions()).thenReturn(List.of(session));
        when(estadisticasClient.getStatsByCoach(anyMap())).thenReturn(Map.of("coach1", 1));

        ResponseEntity<?> response = controller.getSessionStatisticsByTrainer();
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();

        assertTrue(apiResponse.isSuccess());
        assertEquals("Estadísticas obtenidas correctamente", apiResponse.getMessage());
        assertNotNull(apiResponse.getData());
    }

    @Test
    void testGetStudentsBySession() {
        GymSession session = new GymSession();
        session.setId("session1");

        Reservation res = new Reservation();
        res.setGymSessionId(session);

        when(reservationService.getAllReservations()).thenReturn(List.of(res));

        ResponseEntity<?> response = controller.getStudentsBySession();
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();

        assertTrue(apiResponse.isSuccess());
        assertEquals("Conteo por sesión generado", apiResponse.getMessage());
        assertNotNull(apiResponse.getData());
    }

    @Test
    void testGetPhysicalProgressByUser() {
        User user = new User();
        user.setId("user1");

        PhysicalProgress progress = new PhysicalProgress();
        progress.setId("p1");
        progress.setRegistrationDate(LocalDate.now());
        progress.setWeight(70);
        progress.setHeight(170);

        when(physicalProgressService.getPhysicalProgressByUserId(any(User.class))).thenReturn(List.of(progress));

        ResponseEntity<?> response = controller.getPhysicalProgressByUser("user1");
        ApiResponse<?> apiResponse = (ApiResponse<?>) response.getBody();

        assertTrue(apiResponse.isSuccess());
        assertEquals("Progreso físico obtenido correctamente", apiResponse.getMessage());
        assertNotNull(apiResponse.getData());
    }

}
