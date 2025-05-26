package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.Reservation;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.ReservationRepository;
import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationScheduleServiceTest {
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private ReservationScheduleService reservationScheduleService;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        userRepository = mock(UserRepository.class);
        reservationScheduleService = new ReservationScheduleService(reservationRepository, userRepository);
    }

    @Test
    void createWeeklyReservations_createsNewReservationsForPastReservations() {
        // Arrange
        User user = new User();
        user.setId("user1");

        GymSession gymSession = new GymSession();
        gymSession.setId("gym1");

        Reservation pastReservation = new Reservation();
        pastReservation.setUserId(user);
        pastReservation.setGymSessionId(gymSession);  // Ahora no es null
        pastReservation.setReservationDate(LocalDateTime.now().minusDays(7)); // reserva pasada
        pastReservation.setState(null);

        Reservation futureReservation = new Reservation();
        futureReservation.setUserId(user);
        futureReservation.setGymSessionId(gymSession);
        futureReservation.setReservationDate(LocalDateTime.now().plusDays(7)); // reserva futura
        futureReservation.setState(null);

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(reservationRepository.findByUserId(user)).thenReturn(List.of(pastReservation, futureReservation));

        // Act
        reservationScheduleService.createWeeklyReservations();

        // Assert
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository, times(1)).save(captor.capture());

        Reservation createdReservation = captor.getValue();

        assertEquals(user, createdReservation.getUserId());
        assertEquals(gymSession, createdReservation.getGymSessionId());
        assertEquals(pastReservation.getReservationDate().plusWeeks(1), createdReservation.getReservationDate());
        assertEquals(pastReservation.getState(), createdReservation.getState());
    }
}
