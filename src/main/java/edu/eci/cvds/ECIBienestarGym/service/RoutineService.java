package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.embeddables.Exercise;
import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
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

    public Routine getRoutineById(String id){
        return routineRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró la rutina"));
    }

    public List<Routine> getRoutinesByName(String name){
        return  routineRepository.findByRoutineName(name);
    }

    public List<Routine> getRoutinesByExercises(List<Exercise> exercises) {
        return routineRepository.findByExercises(exercises);
    }


    public List<Routine> getRoutinesByDifficulty(DifficultyLevel difficultyLevel) {
        return routineRepository.findByDifficult(difficultyLevel);
    }

}
