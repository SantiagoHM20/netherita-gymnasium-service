package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ReportDTO;
import edu.eci.cvds.ECIBienestarGym.dto.ReportEntryDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.embeddables.ReportEntry;
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

    public Report getReportById(String id){return reportRepository.findById(id).orElseThrow(() -> new GYMException(GYMException.REPORT_NOT_FOUND));}

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

    public Report updateReport(String id, ReportDTO reportDTO) throws GYMException {
        Report report = reportRepository.findById(id).orElseThrow(() -> new  GYMException(GYMException.REPORT_NOT_FOUND));
        return reportRepository.save(mapToReport(reportDTO, report));
    }

    public void deleteReport(String id) throws GYMException {
        reportRepository.findById(id)
            .orElseThrow(() -> new  GYMException(GYMException.REPORT_NOT_FOUND));
        reportRepository.deleteById(id);
    }

    private Report mapToReport(ReportDTO reportDTO, Report report) {
        report.setCoachId(mapToUser(reportDTO.getCoachId()));
        report.setEntries(reportDTO.getEntries().stream().map(this::mapToReportEntry).toList());
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

    private ReportEntry mapToReportEntry(ReportEntryDTO reportEntryDTO) {
        ReportEntry reportEntry = new ReportEntry();
        reportEntry.setLabel(reportEntryDTO.getLabel());
        reportEntry.setData(reportEntryDTO.getData());
        return reportEntry;
    }


}
