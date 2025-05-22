package edu.eci.cvds.ECIBienestarGym.dto;

import edu.eci.cvds.ECIBienestarGym.enums.Gender;
import edu.eci.cvds.ECIBienestarGym.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private Gender gender;
    private boolean registered;
    private LocalDate registrationDate;

    public UserDTO(String id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserDTO(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public UserDTO(String id) {
        this.id = id;

    }
}
