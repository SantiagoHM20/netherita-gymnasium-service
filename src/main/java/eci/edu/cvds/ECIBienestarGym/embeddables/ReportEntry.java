package eci.edu.cvds.ECIBienestarGym.embeddables;

import jakarta.persistence.Embeddable;

import java.util.Map;
@Embeddable
public class ReportEntry {
    private String label; // Ej: nombre del usuario o sesión
    private Map<String, Object> data; // Ej: {"asistencias": 5, "objetivosCumplidos": true}
}
