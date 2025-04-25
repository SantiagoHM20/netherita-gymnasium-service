package eci.edu.cvds.ECIBienestarGym.model;

import eci.edu.cvds.ECIBienestarGym.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Entity
@Data
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
