package eci.edu.cvds.ECIBienestarGym.service;

import eci.edu.cvds.ECIBienestarGym.embeddables.Exercise;
import eci.edu.cvds.ECIBienestarGym.enums.DifficultyLevel;
import eci.edu.cvds.ECIBienestarGym.model.Routine;
import eci.edu.cvds.ECIBienestarGym.repository.RoutineRepository;
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
