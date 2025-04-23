package eci.edu.cvds.ECIBienestarGym.service;

import eci.edu.cvds.ECIBienestarGym.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
