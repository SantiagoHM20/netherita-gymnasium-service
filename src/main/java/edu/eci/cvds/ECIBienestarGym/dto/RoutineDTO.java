package edu.eci.cvds.ECIBienestarGym.dto;

import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDTO {
    private String id;
    private String routineName;
    private String description;
    private List<ExerciseDTO> exercises;
    private int durationDays;
    private DifficultyLevel difficult;
}

