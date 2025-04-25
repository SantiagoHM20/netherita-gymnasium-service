package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.enums.Status;
import eci.edu.cvds.ECIBienestarGym.model.GymSession;
import eci.edu.cvds.ECIBienestarGym.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    
    List<Reservation> findAll();


    List<Reservation> findByUserId(String userId);

    List<Reservation> findByGymSessionId(GymSession gymSessionId);

    List<Reservation> findByReservationDate(LocalDateTime reservationDate);

    List<Reservation> findReservationByStatus(Status state);

}
