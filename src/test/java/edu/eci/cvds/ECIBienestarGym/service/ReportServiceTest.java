package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ReportDTO;

import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.Report;

import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllReports() {
        List<Report> reports = Arrays.asList(new Report(), new Report());
        when(reportRepository.findAll()).thenReturn(reports);

        List<Report> result = reportService.getAllReports();

        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnReportById() throws GYMException {
        Report report = new Report();
        report.setId("r123");

        when(reportRepository.findById("r123")).thenReturn(Optional.of(report));

        Report result = reportService.getReportById("r123");

        assertEquals("r123", result.getId());
        verify(reportRepository, times(1)).findById("r123");
    }

    @Test
    void shouldThrowExceptionWhenReportNotFound() {
        when(reportRepository.findById("invalid")).thenReturn(Optional.empty());

        GYMException exception = org.junit.jupiter.api.Assertions.assertThrows(GYMException.class, () -> {
            reportService.getReportById("invalid");
        });

        assertEquals(GYMException.REPORT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldReturnReportsByCoach() {
        User coach = new User();
        coach.setId("coach123");

        List<Report> reports = Arrays.asList(new Report(), new Report());
        when(reportRepository.findByCoachId(coach)).thenReturn(reports);

        List<Report> result = reportService.getReportsByCoach(coach);

        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findByCoachId(coach);
    }

    @Test
    void shouldReturnReportsByGeneratedAt() {
        LocalDate date = LocalDate.now();
        List<Report> reports = Arrays.asList(new Report(), new Report());
        when(reportRepository.findByGeneratedAt(date)).thenReturn(reports);

        List<Report> result = reportService.getReportsByGeneratedAt(date);

        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findByGeneratedAt(date);
    }

    @Test
    void shouldReturnReportsByType() {
        ReportType type = ReportType.USO;
        List<Report> reports = Arrays.asList(new Report(), new Report());

        when(reportRepository.findByType(type)).thenReturn(reports);

        List<Report> result = reportService.getReportsByType(type);

        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findByType(type);
    }

    @Test
    void shouldCreateAReport() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId("report123");
        reportDTO.setDescription("This is a test report");
        reportDTO.setGeneratedAt(LocalDate.now());
        reportDTO.setCoachId(new UserDTO("user123"));

        Report mockReport = new Report();
        mockReport.setId("report123");
        mockReport.setDescription("This is a test report");
        mockReport.setGeneratedAt(LocalDate.now());
        mockReport.setCoachId(new User("user123"));

        when(reportRepository.save(any(Report.class))).thenReturn(mockReport);

        Report createdReport = reportService.createReport(reportDTO);

        assertEquals(mockReport, createdReport);
        verify(reportRepository, times(1)).save(any(Report.class));
    }


}