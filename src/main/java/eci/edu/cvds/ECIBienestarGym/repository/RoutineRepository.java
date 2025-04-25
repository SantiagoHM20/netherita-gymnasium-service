package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.embeddables.Exercise;
import eci.edu.cvds.ECIBienestarGym.enums.DifficultyLevel;
import eci.edu.cvds.ECIBienestarGym.model.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends MongoRepository<Routine, String> {

    List<Routine> findAll();


    List<Routine> findByRoutineName(String routineName);

    List<Routine> findByExercises(List<Exercise> exercises);

    List<Routine> findByDifficult(DifficultyLevel difficult);



}
