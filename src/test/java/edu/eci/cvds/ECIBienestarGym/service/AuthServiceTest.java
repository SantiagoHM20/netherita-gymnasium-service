package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceTest {
    private UserRepository userRepository;
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        authService = new AuthService(userRepository);
    }

    @Test
    public void shouldReturnUserWhenEmailAndRoleMatch() throws GYMException {
        String email = "test@example.com";
        Role role = Role.STUDENT;

        User user = new User();
        user.setEmail(email);
        user.setRole(role);

        when(userRepository.findByEmailAndRole(email, role)).thenReturn(Optional.of(user));

        User result = authService.isUserValidate(email, role);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(role, result.getRole());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        String email = "missing@example.com";
        Role role = Role.ADMINISTRATOR;

        when(userRepository.findByEmailAndRole(email, role)).thenReturn(Optional.empty());

        GYMException exception = assertThrows(GYMException.class, () -> {
            authService.isUserValidate(email, role);
        });

        assertEquals(GYMException.USER_NOT_FOUND, exception.getMessage());
    }
}
