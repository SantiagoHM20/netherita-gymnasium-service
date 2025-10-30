package edu.eci.cvds.ECIBienestarGym.dto;

import edu.eci.cvds.ECIBienestarGym.enums.Role;
import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private Role role;
    private String password;
}
