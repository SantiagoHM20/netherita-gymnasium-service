package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.model.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends MongoRepository<Routine, String> {
}
