package edu.eci.cvds.ECIBienestarGym.dto;

import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private String id;
    private UserDTO coachId;
    private List<ReportEntryDTO> entries;
    private LocalDate generatedAt;
    private ReportType type;
    private String description;
}
