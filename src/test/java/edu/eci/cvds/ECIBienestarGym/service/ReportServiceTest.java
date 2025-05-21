package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ReportDTO;

import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
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