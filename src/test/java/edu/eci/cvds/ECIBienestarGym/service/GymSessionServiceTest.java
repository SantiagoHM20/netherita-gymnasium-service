package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.GymSessionDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.GymSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GymSessionServiceTest {

    @Mock
    private GymSessionRepository gymSessionRepository;

    @InjectMocks
    private GymSessionService gymSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ShouldGetAllGymSessions() {
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionRepository.findAll()).thenReturn(mockSessions);

        List<GymSession> sessions = gymSessionService.getAllGymSessions();

        assertEquals(2, sessions.size());
        verify(gymSessionRepository, times(1)).findAll();
    }

    @Test
    void ShouldGetGymSessionById() throws GYMException {
        String id = "sess123";
        GymSession mockSession = new GymSession();
        when(gymSessionRepository.findById(id)).thenReturn(Optional.of(mockSession));

        GymSession session = gymSessionService.getGymSessionById(id);

        assertEquals(mockSession, session);
        verify(gymSessionRepository, times(1)).findById(id);
    }

    @Test
    void ShouldGetGymSessionsByCoachId() {
        User coach = new User();
        coach.setId("coach456");
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionRepository.findByCoachId(coach)).thenReturn(mockSessions);

        List<GymSession> sessions = gymSessionService.getGymSessionsByCoachId(coach);

        assertEquals(2, sessions.size());
        verify(gymSessionRepository, times(1)).findByCoachId(coach);
    }

    @Test
    void ShouldGetGymSessionsByCapacity() {
        int capacity = 30;
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionRepository.findByCapacity(capacity)).thenReturn(mockSessions);

        List<GymSession> sessions = gymSessionService.getGymSessionsByCapacity(capacity);

        assertEquals(2, sessions.size());
        verify(gymSessionRepository, times(1)).findByCapacity(capacity);
    }

    @Test
    void ShouldGetGymSessionsByDate() {
        LocalDate date = LocalDate.now();
        LocalDateTime startOfDay = date.atStartOfDay();
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionRepository.findByDate(date)).thenReturn(mockSessions);

        List<GymSession> sessions = gymSessionService.getGymSessionsByDate(startOfDay);

        assertEquals(2, sessions.size());
        verify(gymSessionRepository, times(1)).findByDate(date);
    }

    @Test
    void ShouldGetGymSessionsByDateAndTime() {
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        LocalDateTime startDateTime = startTime.atDate(date);
        LocalDateTime endDateTime = endTime.atDate(date);
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionRepository.findByDateAndStartTimeBetween(date, startDateTime, endDateTime)).thenReturn(mockSessions);
        List<GymSession> sessions = gymSessionService.getGymSessionsByDateAndTime(date, startDateTime.toLocalTime(), endDateTime.toLocalTime());

        assertEquals(2, sessions.size());
        verify(gymSessionRepository, times(1)).findByDateAndStartTimeBetween(date, startDateTime, endDateTime);
    }

    @Test
    void ShouldGetGymSessionsByStartTimeAndEndTime() {
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionRepository.findByStartTimeAndEndTime(startTime, endTime)).thenReturn(mockSessions);

        List<GymSession> sessions = gymSessionService.getGymSessionsByStartTimeAndEndTime(startTime, endTime);

        assertEquals(2, sessions.size());
        verify(gymSessionRepository, times(1)).findByStartTimeAndEndTime(startTime, endTime);
    }

    @Test
    void ShouldGetGymSessionsByEndTime() {
        LocalTime endTime = LocalTime.of(12, 0);
        List<GymSession> mockSessions = Arrays.asList(new GymSession(), new GymSession());
        when(gymSessionRepository.findByEndTime(endTime)).thenReturn(mockSessions);

        List<GymSession> sessions = gymSessionService.getGymSessionsByEndTime(endTime);

        assertEquals(2, sessions.size());
        verify(gymSessionRepository, times(1)).findByEndTime(endTime);
    }

    @Test
    void ShouldCreateGymSession() {
        UserDTO coachDTO = new UserDTO();
        coachDTO.setId("coach123");
        coachDTO.setName("John Doe");
        coachDTO.setEmail("johndoe@example.com");
        coachDTO.setRole(Role.PROFESOR);

        GymSessionDTO gymSessionDTO = new GymSessionDTO();
        gymSessionDTO.setCoachId(coachDTO); 
        gymSessionDTO.setDate(LocalDate.now());
        gymSessionDTO.setStartTime(LocalTime.of(10, 0));
        gymSessionDTO.setEndTime(LocalTime.of(12, 0));
        gymSessionDTO.setCapacity(20);

        GymSession mockSession = new GymSession();
        when(gymSessionRepository.save(any(GymSession.class))).thenReturn(mockSession);

        GymSession createdSession = gymSessionService.createGymSession(gymSessionDTO);

        assertEquals(mockSession, createdSession);
        verify(gymSessionRepository, times(1)).save(any(GymSession.class));
    }

    @Test
    void ShouldUpdateGymSession() throws GYMException {
        String id = "sess123";

        UserDTO coachDTO = new UserDTO();
        coachDTO.setId("coach123");
        coachDTO.setName("John Doe");
        coachDTO.setEmail("johndoe@example.com");
        coachDTO.setRole(Role.PROFESOR);

        GymSessionDTO gymSessionDTO = new GymSessionDTO();
        gymSessionDTO.setCoachId(coachDTO);
        gymSessionDTO.setDate(LocalDate.now());
        gymSessionDTO.setStartTime(LocalTime.of(10, 0));
        gymSessionDTO.setEndTime(LocalTime.of(12, 0));
        gymSessionDTO.setCapacity(20);

        GymSession mockSession = new GymSession();
        when(gymSessionRepository.findById(id)).thenReturn(Optional.of(mockSession));
        when(gymSessionRepository.save(any(GymSession.class))).thenReturn(mockSession);

        GymSession updatedSession = gymSessionService.updateGymSession(id, gymSessionDTO);

        assertEquals(mockSession, updatedSession);
        verify(gymSessionRepository, times(1)).findById(id);
        verify(gymSessionRepository, times(1)).save(any(GymSession.class));
    }

    @Test
    void ShouldDeleteGymSession() throws GYMException {
        String id = "sess123";
        GymSession mockSession = new GymSession();
        mockSession.setId(id);

        when(gymSessionRepository.findById(id)).thenReturn(Optional.of(mockSession));
        doNothing().when(gymSessionRepository).delete(mockSession);

        gymSessionService.deleteGymSession(id);

        verify(gymSessionRepository, times(1)).findById(id);
        verify(gymSessionRepository, times(1)).delete(mockSession);
    }
}
