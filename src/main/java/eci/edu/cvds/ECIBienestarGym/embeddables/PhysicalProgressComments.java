package eci.edu.cvds.ECIBienestarGym.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
public class PhysicalProgressComments {
    private String comment;
    private LocalDate comment_date;
}
