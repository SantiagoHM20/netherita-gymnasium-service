package edu.eci.cvds.ECIBienestarGym.model;

import edu.eci.cvds.ECIBienestarGym.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;
    @DBRef
    private User userId;
    @DBRef
    private GymSession gymSessionId;
    private LocalDateTime reservationDate;
    private Status state;
}
