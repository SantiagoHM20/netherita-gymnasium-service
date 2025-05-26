package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public List<User> getUsersByRegistrationDate(LocalDate registrationDate) {return userRepository.findByRegistrationDate(registrationDate);}

    public User getUsersById(String id) {return userRepository.findById(id).orElse(null);}

    public User createUser(UserDTO userDTO) throws GYMException {

        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            throw new GYMException(GYMException.USER_NOT_NULL);
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new GYMException(GYMException.USER_NOT_NULL);
        }
        if (userDTO.getRole() == null) {
            throw new GYMException(GYMException.USER_NOT_NULL);
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setGender(userDTO.getGender());
        user.setPassword(userDTO.getPassword());
        user.setRegistrationDate(userDTO.getRegistrationDate());
        user.setRegistered(userDTO.isRegistered());

        return userRepository.save(user);
    }

    public User updateUser(String id, UserDTO userDTO) throws GYMException {
        User user = userRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.USER_NOT_FOUND));
        user.setGender(userDTO.getGender());
        user.setRegistered(userDTO.isRegistered());
        user.setRegistrationDate(LocalDate.now());

        return userRepository.save(user);
    }

    public void deleteUser(String id) throws GYMException {
        if (id == null || id.isEmpty()) {
            throw new GYMException(GYMException.USER_NOT_NULL);
        }

        userRepository.findById(id)
            .orElseThrow(() -> new GYMException(GYMException.USER_NOT_FOUND));

        userRepository.deleteById(id);
    }
}

