package eci.edu.cvds.ECIBienestarGym.service;

import eci.edu.cvds.ECIBienestarGym.enums.Status;
import eci.edu.cvds.ECIBienestarGym.model.GymSession;
import eci.edu.cvds.ECIBienestarGym.model.Reservation;
import eci.edu.cvds.ECIBienestarGym.model.Routine;
import eci.edu.cvds.ECIBienestarGym.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Reservation> getReservationsByUserId(String userId){
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByGymSession(GymSession gymSession){
        return reservationRepository.findByGymSessionId(gymSession);
    }

    public List<Reservation> getReservationsByReservationDate(LocalDateTime reservationDate){
        return reservationRepository.findByReservationDate(reservationDate);
    }

    public List<Reservation> getReservationsByStatus(Status status){
        return reservationRepository.findReservationByStatus(status);
    }







}
