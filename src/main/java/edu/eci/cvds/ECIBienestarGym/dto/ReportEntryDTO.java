package edu.eci.cvds.ECIBienestarGym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportEntryDTO {
    private String label; // Ej: nombre del usuario o sesión
    private Map<String, Object> data; // Ej: {"asistencias": 5, "objetivosCumplidos": true}
}
