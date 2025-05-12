package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ExerciseDTO;
import edu.eci.cvds.ECIBienestarGym.dto.RoutineDTO;
import edu.eci.cvds.ECIBienestarGym.embeddables.Exercise;
import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
import edu.eci.cvds.ECIBienestarGym.enums.ExerciseType;
import edu.eci.cvds.ECIBienestarGym.enums.MuscleGroup;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.Routine;
import edu.eci.cvds.ECIBienestarGym.repository.RoutineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoutineServiceTest {

    @Mock
    private RoutineRepository routineRepository;

    @InjectMocks
    private RoutineService routineService;

    @BeforeEach
    void SetUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ShouldReturnAllRoutines() {
        List<Routine> mockRoutines = Arrays.asList(new Routine(), new Routine());
        when(routineRepository.findAll()).thenReturn(mockRoutines);

        List<Routine> routines = routineService.getAllRoutines();

        assertEquals(2, routines.size());
        verify(routineRepository, times(1)).findAll();
    }

    @Test
    void ShouldReturnRoutineById() throws GYMException {
        String id = "routine123";
        Routine mockRoutine = new Routine();
        when(routineRepository.findById(id)).thenReturn(Optional.of(mockRoutine));

        Routine routine = routineService.getRoutineById(id);

        assertEquals(mockRoutine, routine);
        verify(routineRepository, times(1)).findById(id);
    }

    @Test
    void ShouldReturnRoutinesByName() {
        String name = "Cardio";
        List<Routine> mockRoutines = Arrays.asList(new Routine(), new Routine());
        when(routineRepository.findByName(name)).thenReturn(mockRoutines);

        List<Routine> routines = routineService.getRoutinesByName(name);

        assertEquals(2, routines.size());
        verify(routineRepository, times(1)).findByName(name);
    }

    @Test
    void ShouldReturnRoutinesByExercises() {
        List<Exercise> exercises = Arrays.asList(new Exercise(), new Exercise());
        List<Routine> mockRoutines = Arrays.asList(new Routine(), new Routine());
        when(routineRepository.findByExercises(exercises)).thenReturn(mockRoutines);

        List<Routine> routines = routineService.getRoutinesByExercises(exercises);

        assertEquals(2, routines.size());
        verify(routineRepository, times(1)).findByExercises(exercises);
    }

    @Test
    void ShouldReturnRoutinesByDifficulty() {
        DifficultyLevel difficulty = DifficultyLevel.MEDIUM;
        List<Routine> mockRoutines = Arrays.asList(new Routine(), new Routine());
        when(routineRepository.findByDifficulty(difficulty)).thenReturn(mockRoutines);

        List<Routine> routines = routineService.getRoutinesByDifficulty(difficulty);

        assertEquals(2, routines.size());
        verify(routineRepository, times(1)).findByDifficulty(difficulty);
    }

    @Test
    void ShouldCreateRoutine() {
        RoutineDTO routineDTO = new RoutineDTO();
        routineDTO.setName("Strength");
        routineDTO.setDescription("Strength training routine");
        routineDTO.setDifficulty(DifficultyLevel.HARD);
        routineDTO.setExercises(Arrays.asList(
            new ExerciseDTO("Push-ups", 10, 3, 0, ExerciseType.FUERZA, List.of(MuscleGroup.PECHO))
        ));

        Routine mockRoutine = new Routine();
        when(routineRepository.save(any(Routine.class))).thenReturn(mockRoutine);

        Routine createdRoutine = routineService.createRoutine(routineDTO);

        assertEquals(mockRoutine, createdRoutine);
        verify(routineRepository, times(1)).save(any(Routine.class));
    }

    @Test
    void ShouldUpdateRoutine() throws GYMException {
        String id = "routine123";
        RoutineDTO routineDTO = new RoutineDTO();
        routineDTO.setName("Updated Routine");
        routineDTO.setDescription("Updated description");
        routineDTO.setDifficulty(DifficultyLevel.EASY);
        routineDTO.setExercises(Arrays.asList(
            new ExerciseDTO("Squats", 15, 3, 0, ExerciseType.FUERZA, List.of(MuscleGroup.DORSALES))
        ));

        Routine mockRoutine = new Routine();
        when(routineRepository.findById(id)).thenReturn(Optional.of(mockRoutine));
        when(routineRepository.save(any(Routine.class))).thenReturn(mockRoutine);

        Routine updatedRoutine = routineService.updateRoutine(id, routineDTO);

        assertEquals(mockRoutine, updatedRoutine);
        verify(routineRepository, times(1)).findById(id);
        verify(routineRepository, times(1)).save(any(Routine.class));
    }

    @Test
    void ShouldDeleteRoutine() throws GYMException {
        String id = "routine123";
        Routine mockRoutine = new Routine();
        when(routineRepository.findById(id)).thenReturn(Optional.of(mockRoutine));
        doNothing().when(routineRepository).delete(mockRoutine);

        routineService.deleteRoutine(id);

        verify(routineRepository, times(1)).findById(id);
        verify(routineRepository, times(1)).delete(mockRoutine);
    }
}
