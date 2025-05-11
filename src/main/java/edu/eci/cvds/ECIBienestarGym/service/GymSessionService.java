package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.GymSessionDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.GymSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class GymSessionService {
    private final GymSessionRepository gymSessionRepository;

    public GymSessionService(GymSessionRepository gymSessionRepository) {this.gymSessionRepository = gymSessionRepository;}

    public List<GymSession> getAllGymSessions(){
        return gymSessionRepository.findAll();
    }

    public GymSession getGymSessionById(String id) throws GYMException {return gymSessionRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.GYM_SESION_NOT_FOUND));}

    public List<GymSession> getGymSessionsByCoachId(User coachId){return gymSessionRepository.findByCoachId(coachId);}

    public List<GymSession> getGymSessionsByCapacity(int capacity){return gymSessionRepository.findByCapacity(capacity);}

    public List<GymSession> getGymSessionsByDate(LocalDateTime date){return gymSessionRepository.findByDate(LocalDate.from(date));}

    public List<GymSession> getGymSessionsByDateAndTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        LocalDateTime startDateTime = date.atTime(startTime);
        LocalDateTime endDateTime = date.atTime(endTime);
        return gymSessionRepository.findByDateAndStartTimeBetween(date, startDateTime, endDateTime);
    }

    public List<GymSession> getGymSessionsByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime) {return gymSessionRepository.findByStartTimeAndEndTime(startTime, endTime);}

    public List<GymSession> getGymSessionsByEndTime(LocalTime endTime) {return gymSessionRepository.findByEndTime(endTime);}

    public GymSession createGymSession(GymSessionDTO gymSessionDTO) {
        GymSession gymSession = mapToGymSession(gymSessionDTO);
        return gymSessionRepository.save(gymSession);
    }
    public GymSession updateGymSession(String id, GymSessionDTO gymSessionDTO) throws GYMException {
        GymSession existingSession = gymSessionRepository.findById(id)
            .orElseThrow(() -> new GYMException(GYMException.GYM_SESION_NOT_FOUND));
        GymSession updatedSession = mapToGymSession(gymSessionDTO);
        updatedSession.setId(existingSession.getId());
        return gymSessionRepository.save(updatedSession);
    }

    public void deleteGymSession(String id) throws GYMException {
        GymSession gymSession = gymSessionRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.GYM_SESION_NOT_FOUND));
        gymSessionRepository.delete(gymSession);
    }

    private GymSession mapToGymSession(GymSessionDTO gymSessionDTO) {
        GymSession gymSession = new GymSession();
        gymSession.setId(gymSessionDTO.getId());
        gymSession.setCoachId(mapToUser(gymSessionDTO.getCoachId()));
        gymSession.setDate(gymSessionDTO.getDate());
        gymSession.setStartTime(gymSessionDTO.getStartTime());
        gymSession.setEndTime(gymSessionDTO.getEndTime());
        gymSession.setCapacity(gymSessionDTO.getCapacity());
        gymSession.setCurrentReservations(gymSessionDTO.getCurrentReservations());
        return gymSession;
    }

    private User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }

}
