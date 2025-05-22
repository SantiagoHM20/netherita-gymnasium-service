package edu.eci.cvds.ECIBienestarGym.model;

import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiResponseTest {
    @Test
    void testConstructorWithIdNameEmailRole() {
        String id = "user123";
        String name = "Juan Pérez";
        String email = "juan@example.com";
        Role role = Role.ADMINISTRADOR; // asumiendo que tienes este enum

        UserDTO user = new UserDTO(id, name, email, role);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
    }
}
