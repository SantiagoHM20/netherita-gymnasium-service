package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.Report;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Operaciones relacionadas con los reportes")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener todos los reportes")
    public ResponseEntity<ApiResponse<List<Report>>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(new ApiResponse<>(true, "Reportes obtenidos exitosamente", reports));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener un reporte por su ID")
    public ResponseEntity<ApiResponse<Report>> getReportById(
            @Parameter(description = "ID del reporte", example = "abc123") @PathVariable String id) {
        Report report = reportService.getReportById(id);
        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Reporte no encontrado", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Reporte encontrado", report));
    }

    @GetMapping("/coach/{coachId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener reportes por ID del entrenador")
    public ResponseEntity<ApiResponse<List<Report>>> getReportsByCoach(
            @Parameter(description = "ID del entrenador", example = "coach123") @PathVariable String coachId) {
        User coach = new User();
        coach.setId(coachId);
        List<Report> reports = reportService.getReportsByCoach(coach);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reportes del entrenador obtenidos", reports));
    }

    @GetMapping("/date")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener reportes por fecha de generación")
    public ResponseEntity<ApiResponse<List<Report>>> getReportsByGeneratedAt(
            @Parameter(description = "Fecha en formato ISO (yyyy-MM-dd)", example = "2024-05-01")
            @RequestParam("generatedAt") String generatedAt) {
        LocalDate date = LocalDate.parse(generatedAt);
        List<Report> reports = reportService.getReportsByGeneratedAt(date);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reportes encontrados por fecha", reports));
    }

    @GetMapping("/type")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Obtener reportes por tipo")
    public ResponseEntity<ApiResponse<List<Report>>> getReportsByType(
            @Parameter(description = "Tipo de reporte", example = "WEEKLY") @RequestParam("type") ReportType type) {
        List<Report> reports = reportService.getReportsByType(type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reportes encontrados por tipo", reports));
    }
}