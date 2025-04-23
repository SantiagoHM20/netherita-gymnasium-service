package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
}
