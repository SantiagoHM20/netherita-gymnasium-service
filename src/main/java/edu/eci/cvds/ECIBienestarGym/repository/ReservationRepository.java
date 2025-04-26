package edu.eci.cvds.ECIBienestarGym.repository;


import edu.eci.cvds.ECIBienestarGym.enums.Status;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.model.Reservation;
import edu.eci.cvds.ECIBienestarGym.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {


    List<Reservation> findByUserId(User userId);

    List<Reservation> findByGymSessionId(GymSession gymSessionId);

    List<Reservation> findByReservationDate(LocalDateTime reservationDate);

    List<Reservation> findReservationByState(Status state);

}
