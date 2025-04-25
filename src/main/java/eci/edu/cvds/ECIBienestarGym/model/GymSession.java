package eci.edu.cvds.ECIBienestarGym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Entity
@Data
public class GymSession {
    @Id
    private String id;
    @DBRef
    private User coachId;
    private LocalDateTime schedule;
    private int capacity;
    private int currentReservations;
}
