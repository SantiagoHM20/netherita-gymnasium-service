package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;
import edu.eci.cvds.ECIBienestarGym.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private JwtUtil jwtUtil;
    
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userRepository, jwtUtil);
    }

    // ...existing test methods...
}
