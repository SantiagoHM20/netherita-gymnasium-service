package edu.eci.cvds.ECIBienestarGym.service;

import java.util.Optional;

import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User isUserValidate(String email, Role role) throws GYMException{
        Optional<User> userOptional = userRepository.findByEmailAndRole(email, role);
        if(userOptional.isPresent()){
            return userOptional.get();
        } else {
            throw new GYMException(GYMException.USER_NOT_FOUND);
        }
    }
}
