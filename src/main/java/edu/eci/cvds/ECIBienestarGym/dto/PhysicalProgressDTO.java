package edu.eci.cvds.ECIBienestarGym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
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
    private float arms;
    private float legs;
    private float shoulders;
    private List<PhysicalProgressCommentsDTO> comments;
}
