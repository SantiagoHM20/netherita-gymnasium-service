package edu.eci.cvds.ECIBienestarGym.repository;


import edu.eci.cvds.ECIBienestarGym.model.PhysicalProgress;
import edu.eci.cvds.ECIBienestarGym.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhysicalProgressRepository extends MongoRepository<PhysicalProgress, String> {


    List<PhysicalProgress> findByUserId(User userId);
    List<PhysicalProgress> findByRegistrationDate(LocalDate registrationDate);






}
