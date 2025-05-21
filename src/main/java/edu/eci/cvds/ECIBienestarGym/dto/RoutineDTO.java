package edu.eci.cvds.ECIBienestarGym.dto;

import edu.eci.cvds.ECIBienestarGym.enums.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDTO {
    private String id;
    private String name;
    private String description;
    private List<ExerciseDTO> exercises;
    private int durationDays;
    private DifficultyLevel difficulty;
}

