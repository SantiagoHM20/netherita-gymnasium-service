package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ReportDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.Report;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.ReportRepository;
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

    public Report getReportById(String id) throws GYMException{return reportRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.REPORT_NOT_FOUND));}

    public List<Report> getReportsByCoach(User coachId){
        return reportRepository.findByCoachId(coachId);
    }

    public List<Report> getReportsByGeneratedAt(LocalDate generatedAt){return reportRepository.findByGeneratedAt(generatedAt);}

    public List<Report> getReportsByType(ReportType type){
        return reportRepository.findByType(type);
    }

    public Report createReport(ReportDTO reportDTO) {
        Report report = new Report();
        return reportRepository.save(mapToReport(reportDTO, report));
    }

    private Report mapToReport(ReportDTO reportDTO, Report report) {
        report.setCoachId(mapToUser(reportDTO.getCoachId()));
        report.setGeneratedAt(reportDTO.getGeneratedAt());
        report.setType(reportDTO.getType());
        report.setDescription(reportDTO.getDescription());
        return report;
    }

    private User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }
    
}
