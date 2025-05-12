package edu.eci.cvds.ECIBienestarGym.dto;

import java.util.List;

import edu.eci.cvds.ECIBienestarGym.enums.ExerciseType;
import edu.eci.cvds.ECIBienestarGym.enums.MuscleGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    private String name;
    private int repetitions;
    private int sets;
    private int duration;
    private ExerciseType type;
    private List<MuscleGroup> muscleGroup;
}
