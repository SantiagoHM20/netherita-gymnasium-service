package edu.eci.cvds.ECIBienestarGym.dto;

import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private String id;
    private UserDTO coachId;
    private LocalDate generatedAt;
    private ReportType type;
    private String description;
    private List<UserDTO> users;
    private List<GymSessionDTO> sessions;
}
