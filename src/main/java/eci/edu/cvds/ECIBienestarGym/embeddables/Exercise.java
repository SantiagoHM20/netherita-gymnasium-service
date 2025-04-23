package eci.edu.cvds.ECIBienestarGym.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Exercise {
    private String name;
    private int repetitions;
    private int sets;

}
