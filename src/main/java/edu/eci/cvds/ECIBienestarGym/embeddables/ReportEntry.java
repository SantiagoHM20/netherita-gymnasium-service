package edu.eci.cvds.ECIBienestarGym.embeddables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportEntry {
    private String label; // Ej: nombre del usuario o sesión
    private Map<String, Object> data; // Ej: {"asistencias": 5, "objetivosCumplidos": true}
}
