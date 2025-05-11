package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.ReportDTO;
import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import edu.eci.cvds.ECIBienestarGym.exceptions.GYMException;
import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.Report;
import edu.eci.cvds.ECIBienestarGym.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReports() {
        List<Report> mockReports = Arrays.asList(new Report(), new Report());
        when(reportService.getAllReports()).thenReturn(mockReports);

        ResponseEntity<ApiResponse<List<Report>>> response = reportController.getAllReports();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(reportService, times(1)).getAllReports();
    }

    @Test
    void getReportById() throws GYMException {
        String id = "report123";
        Report mockReport = new Report();
        when(reportService.getReportById(id)).thenReturn(mockReport);

        ResponseEntity<ApiResponse<Report>> response = reportController.getReportById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockReport, response.getBody().getData());
        verify(reportService, times(1)).getReportById(id);
    }

    @Test
    void createReport() {
        ReportDTO reportDTO = new ReportDTO();
        Report mockReport = new Report();
        when(reportService.createReport(reportDTO)).thenReturn(mockReport);

        ResponseEntity<ApiResponse<Report>> response = reportController.createReport(reportDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockReport, response.getBody().getData());
        verify(reportService, times(1)).createReport(reportDTO);
    }

    @Test
    void updateReport() throws GYMException {
        String id = "report123";
        ReportDTO reportDTO = new ReportDTO();
        Report mockReport = new Report();
        when(reportService.updateReport(id, reportDTO)).thenReturn(mockReport);

        ResponseEntity<ApiResponse<Report>> response = reportController.updateReport(id, reportDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockReport, response.getBody().getData());
        verify(reportService, times(1)).updateReport(id, reportDTO);
    }

    @Test
    void deleteReport() throws GYMException {
        String id = "report123";
        doNothing().when(reportService).deleteReport(id);

        ResponseEntity<ApiResponse<Void>> response = reportController.deleteReport(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(reportService, times(1)).deleteReport(id);
    }

    @Test
    void getReportsByCoach() {
        String coachId = "coach123";
        List<Report> mockReports = Arrays.asList(new Report(), new Report());
        when(reportService.getReportsByCoach(any())).thenReturn(mockReports);

        ResponseEntity<ApiResponse<List<Report>>> response = reportController.getReportsByCoach(coachId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(reportService, times(1)).getReportsByCoach(argThat(user -> user.getId().equals(coachId)));
    }

    @Test
    void getReportsByGeneratedAt() {
        String dateStr = "2024-05-01";
        List<Report> mockReports = Arrays.asList(new Report(), new Report());
        when(reportService.getReportsByGeneratedAt(LocalDate.parse(dateStr))).thenReturn(mockReports);

        ResponseEntity<ApiResponse<List<Report>>> response = reportController.getReportsByGeneratedAt(dateStr);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(reportService, times(1)).getReportsByGeneratedAt(LocalDate.parse(dateStr));
    }

    @Test
    void getReportsByType() {
        ReportType type = ReportType.ASSISTANCE;
        List<Report> mockReports = Arrays.asList(new Report(), new Report());
        when(reportService.getReportsByType(type)).thenReturn(mockReports);

        ResponseEntity<ApiResponse<List<Report>>> response = reportController.getReportsByType(type);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getData().size());
        verify(reportService, times(1)).getReportsByType(type);
    }

    @Test
    void getReportById_NotFound() throws GYMException {
        String id = "nonexistent123";
        when(reportService.getReportById(id)).thenReturn(null);
        ResponseEntity<ApiResponse<Report>> response = reportController.getReportById(id);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(false, response.getBody().isSuccess());
        assertEquals("Reporte no encontrado", response.getBody().getMessage());
        verify(reportService, times(1)).getReportById(id);
    }

}