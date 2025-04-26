package edu.eci.cvds.ECIBienestarGym.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "gymSessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GymSession {
    @Id
    private String id;
    @DBRef
    private User coachId;
    private LocalDateTime schedule;
    private int capacity;
    private int currentReservations;
}
