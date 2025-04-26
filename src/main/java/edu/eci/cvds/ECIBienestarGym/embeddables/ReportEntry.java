package edu.eci.cvds.ECIBienestarGym.embeddables;

import lombok.Data;

import java.util.Map;

@Data
public class ReportEntry {
    private String label; // Ej: nombre del usuario o sesión
    private Map<String, Object> data; // Ej: {"asistencias": 5, "objetivosCumplidos": true}
}
