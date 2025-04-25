package eci.edu.cvds.ECIBienestarGym.service;

import eci.edu.cvds.ECIBienestarGym.enums.ReportType;
import eci.edu.cvds.ECIBienestarGym.model.Report;
import eci.edu.cvds.ECIBienestarGym.model.Routine;
import eci.edu.cvds.ECIBienestarGym.model.User;
import eci.edu.cvds.ECIBienestarGym.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> getAllReports(){
    return reportRepository.findAll();
    }

    public Report getReportById(String id){
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el reporte"));
    }

    public List<Report> getReportsByCoach(User coachId){
        return reportRepository.findByCoachId(coachId);
    }

    public List<Report> getReportsByGeneratedAt(LocalDate generatedAt){
        return reportRepository.findByGeneratedAt(generatedAt);
    }

    public List<Report> getReportsByType(ReportType type){
        return reportRepository.findByType(type);
    }

}
