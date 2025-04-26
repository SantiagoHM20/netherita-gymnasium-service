package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.GymSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GymSessionService {
    private final GymSessionRepository gymSessionRepository;

    public GymSessionService(GymSessionRepository gymSessionRepository) {
        this.gymSessionRepository = gymSessionRepository;
    }

    public List<GymSession> getAllGymSessions(){
        return gymSessionRepository.findAll();
    }

    public GymSession getGymSessionById(String id) {
        return gymSessionRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró la sesión de gimnasio"));
    }

    public List<GymSession> getGymSessionsByCoachId(User coachId){
        return gymSessionRepository.findByCoachId(coachId);
    }

    public List<GymSession> getGymSessionsByCapacity(int capacity){
        return gymSessionRepository.findByCapacity(capacity);
    }

    public List<GymSession> getGymSessionsBySchedule(LocalDateTime schedule){
        return gymSessionRepository.findBySchedule(schedule);
    }

}
