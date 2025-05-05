package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
        String id = "user123";
        User mockUser = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        User user = userService.getUsersById(id);

        assertEquals(mockUser, user);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void getUsersByRole() {
        Role role = Role.TEACHER;
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findByRole(role)).thenReturn(mockUsers);

        List<User> users = userService.getUsersByRole(role);

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findByRole(role);
    }

    @Test
        void createUser() {
            UserDTO userDTO = new UserDTO();
            userDTO.setId("user123");
            userDTO.setName("John Doe");
            userDTO.setEmail("johndoe@example.com");
            userDTO.setRole(Role.STUDENT);

            User mockUser = new User();
            mockUser.setId("user123");
            mockUser.setName("John Doe");
            mockUser.setEmail("johndoe@example.com");
            mockUser.setRole(Role.STUDENT);

            when(userRepository.save(any(User.class))).thenReturn(mockUser);

            User createdUser = userService.createUser(userDTO);

            assertEquals(mockUser.getId(), createdUser.getId());
            assertEquals(mockUser.getName(), createdUser.getName());
            assertEquals(mockUser.getEmail(), createdUser.getEmail());
            assertEquals(mockUser.getRole(), createdUser.getRole());

            verify(userRepository, times(1)).save(any(User.class));
        }

    @Test
    void updateUser() {
        String id = "user123";
        UserDTO userDTO = new UserDTO();
        User mockUser = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User updatedUser = userService.updateUser(id, userDTO);

        assertEquals(mockUser, updatedUser);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser() {
        String id = "user123";
        User mockUser = new User();
        mockUser.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));
        doNothing().when(userRepository).deleteById(id);

        userService.deleteUser(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).deleteById(id);
    }
}