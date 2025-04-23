package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.model.GymSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymSessionRepository extends MongoRepository<GymSession, String> {
}
