package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ExerciseDTO;
import edu.eci.cvds.ECIBienestarGym.dto.RoutineDTO;
import edu.eci.cvds.ECIBienestarGym.embeddables.Exercise;
import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.Routine;
import edu.eci.cvds.ECIBienestarGym.repository.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {
    private final RoutineRepository routineRepository;

    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    public List<Routine> getAllRoutines(){
        return routineRepository.findAll();
    }

    public Routine getRoutineById(String id){return routineRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.ROUTINE_NOT_FOUND));}

    public List<Routine> getRoutinesByName(String name){return  routineRepository.findByName(name);}

    public List<Routine> getRoutinesByExercises(List<Exercise> exercises) {return routineRepository.findByExercises(exercises);}

    public List<Routine> getRoutinesByDifficulty(DifficultyLevel difficultyLevel) {return routineRepository.findByDifficulty(difficultyLevel);}

     public Routine createRoutine(RoutineDTO routineDTO) {
        Routine routine = new Routine();
        routine.setName(routineDTO.getName());
        routine.setDescription(routineDTO.getDescription());
        routine.setDifficulty(routineDTO.getDifficulty());
        routine.setExercises(mapToExercises(routineDTO.getExercises()));
        return routineRepository.save(routine);
    }

    private List<Exercise> mapToExercises(List<ExerciseDTO> exerciseDTOs) {
        return exerciseDTOs.stream()
                .map(dto -> new Exercise(dto.getName(), dto.getRepetitions(), dto.getSets(), dto.getDuration(), dto.getType()))
                .toList();
    }

     public Routine updateRoutine(String id, RoutineDTO routineDTO) throws GYMException {
        Routine routine = routineRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.ROUTINE_NOT_FOUND));
        routine.setName(routineDTO.getName());
        routine.setDescription(routineDTO.getDescription());
        routine.setDifficulty(routineDTO.getDifficulty());
        routine.setExercises(mapToExercises(routineDTO.getExercises()));
        return routineRepository.save(routine);
    }

    public void deleteRoutine(String id) throws GYMException {
        Routine routine = routineRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.ROUTINE_NOT_FOUND));
        routineRepository.delete(routine);
    }
}
