package edu.eci.cvds.ECIBienestarGym.model;

import edu.eci.cvds.ECIBienestarGym.embeddables.ReportEntry;
import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reports")
public class Report {
    @Id
    private String id;
    @DBRef
    private User coachId;
    @Field("entries")
    private List<ReportEntry> entries;
    private LocalDate generatedAt;
    private ReportType type;
    private String description;






}
