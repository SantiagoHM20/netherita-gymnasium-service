package edu.eci.cvds.ECIBienestarGym.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.cvds.ECIBienestarGym.dto.AuthRequest;
import edu.eci.cvds.ECIBienestarGym.dto.AuthResponse;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;
import edu.eci.cvds.ECIBienestarGym.util.JwtUtil;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User isUserValidate(String email, Role role) throws GYMException {
        Optional<User> userOptional = userRepository.findByEmailAndRole(email, role);
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new GYMException(GYMException.USER_NOT_FOUND);
        }
    }
    
    public AuthResponse authenticate(AuthRequest authRequest) throws GYMException {
        User user = isUserValidate(authRequest.getEmail(), authRequest.getRole());
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user);
    }
}
