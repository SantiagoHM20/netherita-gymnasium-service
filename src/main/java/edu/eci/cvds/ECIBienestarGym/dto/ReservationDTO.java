package edu.eci.cvds.ECIBienestarGym.dto;

import edu.eci.cvds.ECIBienestarGym.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String id;
    private UserDTO userId;
    private GymSessionDTO gymSessionId;
    private LocalDateTime reservationDate;
    private Status state;
}
