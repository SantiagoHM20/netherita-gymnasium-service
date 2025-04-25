package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.model.GymSession;
import eci.edu.cvds.ECIBienestarGym.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GymSessionRepository extends MongoRepository<GymSession, String> {
    List<GymSession> findAll();
    List<GymSession> findByCoachId(User coachId);
    List<GymSession> findByCapacity(int capacity);
    List<GymSession> findBySchedule(LocalDateTime schedule);
}
