package eci.edu.cvds.ECIBienestarGym.service;

import eci.edu.cvds.ECIBienestarGym.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}
