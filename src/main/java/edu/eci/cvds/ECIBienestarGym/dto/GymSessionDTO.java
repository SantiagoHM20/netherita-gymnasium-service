package edu.eci.cvds.ECIBienestarGym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GymSessionDTO {
    private String id;
    private UserDTO coachId;
    private LocalDateTime schedule;
    private int capacity;
    private int currentReservations;
}
