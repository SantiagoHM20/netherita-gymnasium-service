package eci.edu.cvds.ECIBienestarGym.model;


import eci.edu.cvds.ECIBienestarGym.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private Role role;
    private LocalDate registrationDate;

}
