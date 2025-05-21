package edu.eci.cvds.ECIBienestarGym.model;

import edu.eci.cvds.ECIBienestarGym.embeddables.PhysicalProgressComments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "physicalProgress")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalProgress {
    @Id
    private String id;
    @DBRef
    private User userId;
    @DBRef
    private Routine routine;
    private String goal;
    private LocalDate registrationDate;
    private float weight;
    private float height;
    private float waists;
    private float chest;
    private float rightarm;
    private float leftarm;
    private float legs;
    private float shoulders;
    @Field("comments")
    private List<PhysicalProgressComments> comments;
}
