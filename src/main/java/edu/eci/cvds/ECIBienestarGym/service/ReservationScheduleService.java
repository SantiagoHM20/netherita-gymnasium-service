package edu.eci.cvds.ECIBienestarGym.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ECIBienestarGym.model.Reservation;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.ReservationRepository;
import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;

@Service
public class ReservationScheduleService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public ReservationScheduleService(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    /**
     * This method is scheduled to run every Monday at 00:00.
     * It creates new reservations for users based on their previous reservations.
     */
    @Scheduled(cron = "0 0 0 * * MON") // Every Monday at 00:00
    public void createWeeklyReservations() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            List<Reservation> reservations = reservationRepository.findByUserId(user);
            for (Reservation reservation : reservations) {
                if (reservation.getReservationDate().isBefore(LocalDateTime.now())) {
                    Reservation newReservation = new Reservation();
                    newReservation.setUserId(user);
                    newReservation.setGymSessionId(reservation.getGymSessionId());
                    newReservation.setReservationDate(reservation.getReservationDate().plusWeeks(1));
                    newReservation.setState(reservation.getState());
                    reservationRepository.save(newReservation);
                }
            }
        }
    }
}
