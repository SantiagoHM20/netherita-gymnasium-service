package eci.edu.cvds.ECIBienestarGym.model;

import eci.edu.cvds.ECIBienestarGym.embeddables.Exercise;
import eci.edu.cvds.ECIBienestarGym.enums.DifficultyLevel;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Routine {
    @Id
    private int id;
    private String routineName;
    private String description;
    @ElementCollection
    private List<Exercise> exercises;
    private int durationDays;
    private DifficultyLevel difficult;
}
