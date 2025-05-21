package edu.eci.cvds.ECIBienestarGym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GymSessionDTO {
    private String id;
    private UserDTO coachId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int capacity;
    private int currentReservations;
    private List<Boolean> attendance;
    private List<UserDTO> users;
}
