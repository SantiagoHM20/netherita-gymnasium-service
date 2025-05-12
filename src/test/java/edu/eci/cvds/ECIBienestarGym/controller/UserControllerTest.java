package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllUsersWhenGetAllUsersIsCalled() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<ApiResponse<List<User>>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void shouldReturnUserByIdWhenValidIdIsProvided() {
        String id = "user123";
        User mockUser = new User();
        when(userService.getUsersById(id)).thenReturn(mockUser);

        ResponseEntity<ApiResponse<User>> response = userController.getUserById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody().getData());
        verify(userService, times(1)).getUsersById(id);
    }

    @Test
    void shouldReturnUserByEmailWhenValidEmailIsProvided() {
        String email = "johndoe@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        when(userService.getUsersByEmail(email)).thenReturn(Optional.of(mockUser));

        ResponseEntity<ApiResponse<Optional<User>>> response = userController.getUserByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody().getData().orElse(null));
        verify(userService, times(1)).getUsersByEmail(email);
    }

    @Test
    void shouldReturnUsersByRegistrationDateWhenValidDateIsGiven() {
        LocalDate date = LocalDate.now();
        String formattedDate = date.toString(); // Convierte LocalDate a String
        List<User> mockUsers = Arrays.asList(new User(), new User());

        when(userService.getUsersByRegistrationDate(date)).thenReturn(mockUsers);

        ResponseEntity<ApiResponse<List<User>>> response = userController.getUsersByRegistrationDate(formattedDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getData().size());
        verify(userService, times(1)).getUsersByRegistrationDate(date);
    }

    @Test
    void shouldReturnUsersByRoleWhenValidRoleIsProvided() {
        Role role = Role.STUDENT;
        List<User> mockUsers = Arrays.asList(new User(), new User());

        when(userService.getUsersByRole(role)).thenReturn(mockUsers);

        ResponseEntity<ApiResponse<List<User>>> response = userController.getUsersByRole(role);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getData().size());
        verify(userService, times(1)).getUsersByRole(role);
    }

    @Test
    void shouldReturnUsersByNameWhenValidNameIsGiven() {
        String name = "John Doe";
        List<User> mockUsers = Arrays.asList(new User(), new User());

        when(userService.getUsersByName(name)).thenReturn(mockUsers);

        ResponseEntity<ApiResponse<List<User>>> response = userController.getUsersByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getData().size());
        verify(userService, times(1)).getUsersByName(name);
    }

    @Test
    void shouldCreateUserWhenValidUserDTOIsProvided() throws GYMException {
        UserDTO userDTO = new UserDTO();
        User mockUser = new User();
        when(userService.createUser(userDTO)).thenReturn(mockUser);

        ResponseEntity<ApiResponse<User>> response = userController.createUser(userDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody().getData());
        verify(userService, times(1)).createUser(userDTO);
    }

    @Test
    void shouldUpdateUserWhenValidIdAndUserDTOAreProvided() throws GYMException {
        String id = "user123";
        UserDTO userDTO = new UserDTO();
        User mockUser = new User();
        when(userService.updateUser(id, userDTO)).thenReturn(mockUser);

        ResponseEntity<ApiResponse<User>> response = userController.updateUser(id, userDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody().getData());
        verify(userService, times(1)).updateUser(id, userDTO);
    }

    @Test
    void shouldDeleteUserWhenValidIdIsGiven() throws GYMException {
        String id = "user123";
        doNothing().when(userService).deleteUser(id);

        ResponseEntity<ApiResponse<Void>> response = userController.deleteUser(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(id);
    }

    @Test
    void shouldReturnNotFoundWhenUserByEmailDoesNotExist() {
        String email = "nonexistent@example.com";
        when(userService.getUsersByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<Optional<User>>> response = userController.getUserByEmail(email);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado", response.getBody().getMessage());
        verify(userService, times(1)).getUsersByEmail(email);
    }
}
