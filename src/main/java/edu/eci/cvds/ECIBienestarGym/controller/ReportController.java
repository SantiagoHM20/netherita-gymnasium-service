package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import edu.eci.cvds.ECIBienestarGym.model.Report;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Operaciones relacionadas con los reportes")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los reportes")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un reporte por su ID")
    public Report getReportById(@PathVariable String id) {
        return reportService.getReportById(id);
    }

    @GetMapping("/coach/{coachId}")
    @Operation(summary = "Obtener reportes por ID del entrenador")
    public List<Report> getReportsByCoach(@PathVariable String coachId) {
        User coach = new User();
        coach.setId(coachId);
        return reportService.getReportsByCoach(coach);
    }

    @GetMapping("/date")
    @Operation(summary = "Obtener reportes por fecha de generación")
    public List<Report> getReportsByGeneratedAt(@RequestParam("generatedAt") String generatedAt) {
        LocalDate date = LocalDate.parse(generatedAt);
        return reportService.getReportsByGeneratedAt(date);
    }

    @GetMapping("/type")
    @Operation(summary = "Obtener reportes por tipo")
    public List<Report> getReportsByType(@RequestParam("type") ReportType type) {
        return reportService.getReportsByType(type);
    }
}
