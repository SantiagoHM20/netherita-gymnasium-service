package eci.edu.cvds.ECIBienestarGym.model;

import eci.edu.cvds.ECIBienestarGym.embeddables.PhysicalProgressComments;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class PhysicalProgress {
    @Id
    private int id;
    @DBRef
    private int userId;
    @DBRef
    private Routine routine;
    private String goal;
    private LocalDate registrationDate;
    private float weight;
    private float height;
    private float waists;
    private float chest;
    private float arms;
    private float legs;
    private float shoulders;
    @ElementCollection
    private List<PhysicalProgressComments> comments;


}
