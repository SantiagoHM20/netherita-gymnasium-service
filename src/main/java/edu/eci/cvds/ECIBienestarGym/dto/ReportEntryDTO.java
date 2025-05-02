package edu.eci.cvds.ECIBienestarGym.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ReportEntryDTO {
    private String label; // Ej: nombre del usuario o sesión
    private Map<String, Object> data; // Ej: {"asistencias": 5, "objetivosCumplidos": true}
}
