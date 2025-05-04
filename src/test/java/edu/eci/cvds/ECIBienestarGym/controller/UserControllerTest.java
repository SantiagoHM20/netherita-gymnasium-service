package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

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
    void getAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userService.getAllUsers()).thenReturn(mockUsers);

        ResponseEntity<ApiResponse<List<User>>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUserById() {
        String id = "user123";
        User mockUser = new User();
        when(userService.getUsersById(id)).thenReturn(mockUser);

        ResponseEntity<ApiResponse<User>> response = userController.getUserById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody().getData());
        verify(userService, times(1)).getUsersById(id);
    }

    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO();
        User mockUser = new User();
        when(userService.createUser(userDTO)).thenReturn(mockUser);

        ResponseEntity<ApiResponse<User>> response = userController.createUser(userDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockUser, response.getBody().getData());
        verify(userService, times(1)).createUser(userDTO);
    }

    @Test
    void updateUser() {
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
    void deleteUser() {
        String id = "user123";
        doNothing().when(userService).deleteUser(id);

        ResponseEntity<ApiResponse<Void>> response = userController.deleteUser(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(id);
    }
}