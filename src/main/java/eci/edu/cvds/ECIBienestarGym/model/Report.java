package eci.edu.cvds.ECIBienestarGym.model;

import eci.edu.cvds.ECIBienestarGym.embeddables.ReportEntry;
import eci.edu.cvds.ECIBienestarGym.enums.ReportType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Report {
    @Id
    private String id;
    @DBRef
    private User coachId;
    @ElementCollection
    private List<ReportEntry> entries;
    private LocalDate generatedAt;
    private ReportType type;
    private String description;






}
