package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.model.PhysicalProgress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalProgressRepository extends MongoRepository<PhysicalProgress, String> {
}
