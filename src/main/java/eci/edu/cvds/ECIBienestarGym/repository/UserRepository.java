package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.enums.Role;
import eci.edu.cvds.ECIBienestarGym.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAll();

    List<User> findByName(String name);

    List<User> findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByRegistrationDate(LocalDate registrationDate);

}
