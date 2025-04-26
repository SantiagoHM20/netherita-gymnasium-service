package edu.eci.cvds.ECIBienestarGym.repository;


import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByName(String name);

    List<User> findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByRegistrationDate(LocalDate registrationDate);

}
