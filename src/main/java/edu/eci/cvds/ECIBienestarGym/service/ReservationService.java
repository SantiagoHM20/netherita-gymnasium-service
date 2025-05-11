package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.GymSessionDTO;
import edu.eci.cvds.ECIBienestarGym.dto.ReservationDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.Status;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.Reservation;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {this.reservationRepository = reservationRepository;}

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(String id) throws GYMException{return reservationRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.RESERVE_NOT_FOUND));}

    public List<Reservation> getReservationsByUserId(User userId){
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByGymSession(GymSession gymSession){return reservationRepository.findByGymSessionId(gymSession);}

    public List<Reservation> getReservationsByReservationDate(LocalDateTime reservationDate){return reservationRepository.findByReservationDate(reservationDate);}

    public List<Reservation> getReservationsByState(Status status){return reservationRepository.findReservationByState(status);}

    public Reservation createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setUserId(mapToUser(reservationDTO.getUserId()));
        reservation.setGymSessionId(mapToGymSession(reservationDTO.getGymSessionId()));
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setState(reservationDTO.getState());
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(String id, ReservationDTO reservationDTO) throws GYMException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.RESERVE_NOT_FOUND));
        reservation.setUserId(mapToUser(reservationDTO.getUserId()));
        reservation.setGymSessionId(mapToGymSession(reservationDTO.getGymSessionId()));
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setState(reservationDTO.getState());
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(String id) throws GYMException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.RESERVE_NOT_FOUND));
        reservationRepository.delete(reservation);
    }

    private User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
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

}
