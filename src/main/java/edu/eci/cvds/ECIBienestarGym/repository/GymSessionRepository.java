package edu.eci.cvds.ECIBienestarGym.repository;


import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface GymSessionRepository extends MongoRepository<GymSession, String> {

    List<GymSession> findByCoachId(User coachId);
    List<GymSession> findByCapacity(int capacity);
    List<GymSession> findByDate(LocalDate date);
    List<GymSession> findByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);
    List<GymSession> findByEndTime(LocalTime endTime);
    List<GymSession> findByDateAndStartTimeBetween(LocalDate date, LocalDateTime startTime, LocalDateTime endTime);
}
