package edu.eci.cvds.ECIBienestarGym.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "gymSessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GymSession {


    @Id
    private String id;
    @DBRef
    private User coachId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int currentReservations;
    private List<User> users = new ArrayList<>();
    private List<Boolean> attendance = new ArrayList<>();
}
