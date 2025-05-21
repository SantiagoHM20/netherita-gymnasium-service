package edu.eci.cvds.ECIBienestarGym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalProgressDTO {
    private String id;
    private UserDTO userId;
    private RoutineDTO routine;
    private String goal;
    private LocalDate registrationDate;
    private float weight;
    private float height;
    private float waists;
    private float chest;
    private float rightarm;
    private float leftarm;
    private float rightleg;
    private float leftleg;
    private List<PhysicalProgressCommentsDTO> comments;
}
