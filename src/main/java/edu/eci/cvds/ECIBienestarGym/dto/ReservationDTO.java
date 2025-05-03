package edu.eci.cvds.ECIBienestarGym.dto;

import edu.eci.cvds.ECIBienestarGym.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String id;
    private UserDTO userId;
    private GymSessionDTO gymSessionId;
    private LocalDateTime reservationDate;
    private Status state;
}
