package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.GymSessionDTO;
import edu.eci.cvds.ECIBienestarGym.dto.ReservationDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.Status;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.Reservation;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllReservations() {
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findAll()).thenReturn(mockReservations);

        List<Reservation> reservations = reservationService.getAllReservations();

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnReservationById() throws GYMException {
        String id = "res123";
        Reservation mockReservation = new Reservation();
        when(reservationRepository.findById(id)).thenReturn(Optional.of(mockReservation));

        Reservation reservation = reservationService.getReservationById(id);

        assertEquals(mockReservation, reservation);
        verify(reservationRepository, times(1)).findById(id);
    }

    @Test
    void shouldReturnReservationsByUserId() {
        User user = new User();
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findByUserId(user)).thenReturn(mockReservations);

        List<Reservation> reservations = reservationService.getReservationsByUserId(user);

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findByUserId(user);
    }

    @Test
    void shouldReturnReservationsByGymSession() {
        GymSession gymSession = new GymSession();
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findByGymSessionId(gymSession)).thenReturn(mockReservations);

        List<Reservation> reservations = reservationService.getReservationsByGymSession(gymSession);

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findByGymSessionId(gymSession);
    }

    @Test
    void shouldReturnReservationsByReservationDate() {
        LocalDateTime reservationDate = LocalDateTime.now();
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findByReservationDate(reservationDate)).thenReturn(mockReservations);

        List<Reservation> reservations = reservationService.getReservationsByReservationDate(reservationDate);

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findByReservationDate(reservationDate);
    }

    @Test
    void shouldReturnReservationsByState() {
        Status status = Status.APROBADO;
        List<Reservation> mockReservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findReservationByState(status)).thenReturn(mockReservations);

        List<Reservation> reservations = reservationService.getReservationsByState(status);

        assertEquals(2, reservations.size());
        verify(reservationRepository, times(1)).findReservationByState(status);
    }

   @Test
    void shouldCreateMainReservationSuccessfully() {
        // Arrange
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(new UserDTO("user123", "John Doe", "johndoe@example.com"));
        reservationDTO.setGymSessionId(new GymSessionDTO(
                "session123",
                new UserDTO("coach123", "Jane Doe", "janedoe@example.com"),
                LocalDate.now(),
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                20,
                5,
                Collections.emptyList(),
                Collections.emptyList()
        ));
        LocalDateTime now = LocalDateTime.now();
        reservationDTO.setReservationDate(now);
        reservationDTO.setState(Status.APROBADO);


        User user = new User("user123", "John Doe", "johndoe@example.com");
        GymSession gymSession = new GymSession();
        gymSession.setId("session123");

        Reservation mockReservation = new Reservation();
        mockReservation.setId("res123");
        mockReservation.setUserId(user);
        mockReservation.setGymSessionId(gymSession);
        mockReservation.setReservationDate(now);
        mockReservation.setState(Status.APROBADO);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);

        // Act
        Reservation createdReservation = reservationService.createReservation(reservationDTO);

        // Assert
        assertEquals(mockReservation.getId(), createdReservation.getId());
        assertEquals(mockReservation.getUserId(), createdReservation.getUserId());
        assertEquals(mockReservation.getGymSessionId(), createdReservation.getGymSessionId());
        assertEquals(mockReservation.getReservationDate(), createdReservation.getReservationDate());
        assertEquals(mockReservation.getState(), createdReservation.getState());
    }

    @Test
    void shouldCreateRecurringReservationsAfterMainReservation() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(new UserDTO("user123", "John Doe", "johndoe@example.com"));
        reservationDTO.setGymSessionId(new GymSessionDTO(
                "session123",
                new UserDTO("coach123", "Jane Doe", "janedoe@example.com"),
                LocalDate.now(),
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                20,
                5,
                Collections.emptyList(),
                Collections.emptyList()
        ));
        LocalDateTime now = LocalDateTime.now();
        reservationDTO.setReservationDate(now);
        reservationDTO.setState(Status.APROBADO);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        // Act
        reservationService.createReservation(reservationDTO);

        // Assert
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository, times(6)).save(captor.capture());

        List<Reservation> savedReservations = captor.getAllValues();

        for (int i = 0; i < 6; i++) {
            assertEquals(now.plusWeeks(i), savedReservations.get(i).getReservationDate());
        }
    }

    @Test
    void shouldUpdateReservationSuccessfully() throws GYMException {
        String id = "res123";
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUserId(new UserDTO("user123", "John Doe", "johndoe@example.com"));
        reservationDTO.setGymSessionId(new GymSessionDTO(
                "session123",
                new UserDTO("coach123", "Jane Doe", "janedoe@example.com"),
                LocalDate.now(),
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                20,
                5,
                Collections.emptyList(),
                Collections.emptyList()
        ));
        reservationDTO.setReservationDate(LocalDateTime.now());
        reservationDTO.setState(Status.APROBADO);

        Reservation mockReservation = new Reservation();
        mockReservation.setId(id);

        when(reservationRepository.findById(id)).thenReturn(Optional.of(mockReservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);

        Reservation updatedReservation = reservationService.updateReservation(id, reservationDTO);

        assertEquals(mockReservation, updatedReservation);
        verify(reservationRepository, times(1)).findById(id);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void shouldDeleteReservationSuccessfully() throws GYMException {
        String id = "res123";
        Reservation mockReservation = new Reservation();
        mockReservation.setId(id);

        when(reservationRepository.findById(id)).thenReturn(Optional.of(mockReservation));
        doNothing().when(reservationRepository).delete(mockReservation);

        reservationService.deleteReservation(id);

        verify(reservationRepository, times(1)).findById(id);
        verify(reservationRepository, times(1)).delete(mockReservation);
    }
}