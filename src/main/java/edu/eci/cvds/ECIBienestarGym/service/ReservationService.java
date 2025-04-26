package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.enums.Status;
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

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(String id){
        return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró la reserva"));
    }

    public List<Reservation> getReservationsByUserId(User userId){
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByGymSession(GymSession gymSession){
        return reservationRepository.findByGymSessionId(gymSession);
    }

    public List<Reservation> getReservationsByReservationDate(LocalDateTime reservationDate){
        return reservationRepository.findByReservationDate(reservationDate);
    }

    public List<Reservation> getReservationsByState(Status status){
        return reservationRepository.findReservationByState(status);
    }

}
