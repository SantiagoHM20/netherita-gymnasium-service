package eci.edu.cvds.ECIBienestarGym.controller;

import eci.edu.cvds.ECIBienestarGym.service.ReportService;
import org.junit.jupiter.api.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag("Report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {

        this.reportService = reportService;
    }
}
