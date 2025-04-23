package eci.edu.cvds.ECIBienestarGym.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class Recommendation {

    private String message;
    private int coachId;
    private LocalDate date;
}
