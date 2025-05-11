package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ExerciseDTO;
import edu.eci.cvds.ECIBienestarGym.dto.PhysicalProgressDTO;
import edu.eci.cvds.ECIBienestarGym.dto.RoutineDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.ExerciseType;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.PhysicalProgress;
import edu.eci.cvds.ECIBienestarGym.repository.PhysicalProgressRepository;
import edu.eci.cvds.ECIBienestarGym.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PhysicalProgressServiceTest {

    @Mock
    private PhysicalProgressRepository physicalProgressRepository;

    @InjectMocks
    private PhysicalProgressService physicalProgressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPhysicalProgress() {
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());
        when(physicalProgressRepository.findAll()).thenReturn(mockProgress);

        List<PhysicalProgress> progressList = physicalProgressService.getAllPhysicalProgress();

        assertEquals(2, progressList.size());
        verify(physicalProgressRepository, times(1)).findAll();
    }

    @Test
    void getPhysicalProgressById() throws GYMException {
        String id = "progress123";
        PhysicalProgress mockProgress = new PhysicalProgress();
        when(physicalProgressRepository.findById(id)).thenReturn(Optional.of(mockProgress));

        PhysicalProgress progress = physicalProgressService.getPhysicalProgressById(id);

        assertEquals(mockProgress, progress);
        verify(physicalProgressRepository, times(1)).findById(id);
    }

    @Test
    void getPhysicalProgressByDate() {
        LocalDate date = LocalDate.now();
        List<PhysicalProgress> mockProgress = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());
        when(physicalProgressRepository.findByRegistrationDate(date)).thenReturn(mockProgress);

        List<PhysicalProgress> progressList = physicalProgressService.getPhysicalProgressByRegistrationDate(date);

        assertEquals(2, progressList.size());
        verify(physicalProgressRepository, times(1)).findByRegistrationDate(date);
    }

    @Test
    void updatePhysicalProgress() throws GYMException {
        String id = "progress123";

        UserDTO userDTO = new UserDTO();
        userDTO.setId("user123");
        userDTO.setName("John Doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setRole(Role.STUDENT);

        RoutineDTO routineDTO = new RoutineDTO();
        routineDTO.setId("routine123");
        routineDTO.setName("Routine A");
        routineDTO.setDescription("Description of Routine A");
        routineDTO.setExercises(Arrays.asList(
            new ExerciseDTO("Exercise 1", 10, 3, 60, ExerciseType.FUERZA),
            new ExerciseDTO("Exercise 2", 15, 4, 45, ExerciseType.CARDIO)
        ));

        PhysicalProgressDTO progressDTO = new PhysicalProgressDTO();
        progressDTO.setUserId(userDTO);
        progressDTO.setRoutine(routineDTO);
        progressDTO.setWeight(72.0F);
        progressDTO.setHeight(1.76F);
        progressDTO.setRegistrationDate(LocalDate.now());

        PhysicalProgress mockProgress = new PhysicalProgress();
        when(physicalProgressRepository.findById(id)).thenReturn(Optional.of(mockProgress));
        when(physicalProgressRepository.save(any(PhysicalProgress.class))).thenReturn(mockProgress);


        if (physicalProgressService == null) {
            throw new IllegalStateException("physicalProgressService no está inicializado");
        }
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }
        if (progressDTO == null) {
            throw new IllegalArgumentException("El progressDTO no puede ser null");
        }

        PhysicalProgress updatedProgress = physicalProgressService.updatePhysicalProgress(id, progressDTO);
            assertEquals(mockProgress, updatedProgress);
            verify(physicalProgressRepository, times(1)).findById(id);
            verify(physicalProgressRepository, times(1)).save(any(PhysicalProgress.class));
        }

    @Test
    void deletePhysicalProgress() throws GYMException {
        String id = "progress123";

        // Mock del repositorio
        PhysicalProgress mockProgress = new PhysicalProgress();
        mockProgress.setId(id);
        when(physicalProgressRepository.findById(id)).thenReturn(Optional.of(mockProgress));
        doNothing().when(physicalProgressRepository).deleteById(id);

        // Llamar al método del servicio
        physicalProgressService.deletePhysicalProgress(id);

        // Verificar las interacciones
        verify(physicalProgressRepository, times(1)).findById(id);
        verify(physicalProgressRepository, times(1)).deleteById(id);
    }

    @Test
    void createPhysicalProgress() {
        // Crear el DTO de progreso físico
        UserDTO userDTO = new UserDTO();
        userDTO.setId("user123");
        userDTO.setName("John Doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setRole(Role.STUDENT);

        RoutineDTO routineDTO = new RoutineDTO();
        routineDTO.setId("routine123");
        routineDTO.setName("Routine A");
        routineDTO.setDescription("Routine A description");
        routineDTO.setExercises(Arrays.asList(
            new ExerciseDTO("Exercise 1", 10, 3, 60, ExerciseType.FUERZA),
            new ExerciseDTO("Exercise 2", 15, 4, 45, ExerciseType.CARDIO)
        ));

        PhysicalProgressDTO progressDTO = new PhysicalProgressDTO();
        progressDTO.setUserId(userDTO);
        progressDTO.setRoutine(routineDTO);
        progressDTO.setWeight(72.0F);
        progressDTO.setHeight(1.76F);
        progressDTO.setRegistrationDate(LocalDate.now());

        PhysicalProgress mockProgress = new PhysicalProgress();
        when(physicalProgressRepository.save(any(PhysicalProgress.class))).thenReturn(mockProgress);

        // Llamar al método createPhysicalProgress
        PhysicalProgress createdProgress = physicalProgressService.createPhysicalProgress(progressDTO);

        assertEquals(mockProgress, createdProgress);
        verify(physicalProgressRepository, times(1)).save(any(PhysicalProgress.class));
    }

    @Test
    void getPhysicalProgressByUserId() {
        User mockUser = new User();
        mockUser.setId("user123");

        List<PhysicalProgress> mockProgressList = Arrays.asList(new PhysicalProgress(), new PhysicalProgress());

        when(physicalProgressRepository.findByUserId(mockUser)).thenReturn(mockProgressList);

        List<PhysicalProgress> result = physicalProgressService.getPhysicalProgressByUserId(mockUser);

        assertEquals(2, result.size());
        verify(physicalProgressRepository, times(1)).findByUserId(mockUser);
    }

    @Test
    void getPhysicalProgressByUserIdAndDate() {
        User mockUser = new User();
        mockUser.setId("user123");
        LocalDate date = LocalDate.now();

        List<PhysicalProgress> mockProgressList = List.of(new PhysicalProgress());

        when(physicalProgressRepository.findByUserIdAndRegistrationDate(mockUser, date)).thenReturn(mockProgressList);

        List<PhysicalProgress> result = physicalProgressService.getPhysicalProgressByUserIdAndDate(mockUser, date);

        assertEquals(1, result.size());
        verify(physicalProgressRepository, times(1)).findByUserIdAndRegistrationDate(mockUser, date);
    }

    @Test
    void getPhysicalProgressByUserIdAndDateBetween() {
        User mockUser = new User();
        mockUser.setId("user123");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        List<PhysicalProgress> mockProgressList = Arrays.asList(new PhysicalProgress(), new PhysicalProgress(), new PhysicalProgress());

        when(physicalProgressRepository.findByUserIdAndRegistrationDateBetween(mockUser, startDate, endDate)).thenReturn(mockProgressList);

        List<PhysicalProgress> result = physicalProgressService.getPhysicalProgressByUserIdAndDateBetween(mockUser, startDate, endDate);

        assertEquals(3, result.size());
        verify(physicalProgressRepository, times(1)).findByUserIdAndRegistrationDateBetween(mockUser, startDate, endDate);
    }


}