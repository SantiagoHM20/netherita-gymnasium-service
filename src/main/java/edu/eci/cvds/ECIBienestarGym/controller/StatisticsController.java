package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.dto.PhysicalProgressDTO;
import edu.eci.cvds.ECIBienestarGym.model.*;
import edu.eci.cvds.ECIBienestarGym.service.GymSessionService;
import edu.eci.cvds.ECIBienestarGym.service.PhysicalProgressService;
import edu.eci.cvds.ECIBienestarGym.service.ReservationService;
import edu.eci.cvds.ECIBienestarGym.statistic.stats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/stats")
@Tag(name = "Statistics", description = "Controladores relacionados para las estadisticas")
public class StatisticsController {
    private final GymSessionService gymSessionService;
    private final stats estadisticasClient;
    private final ReservationService reservationService;
    private final PhysicalProgressService physicalProgressService;

    public StatisticsController(GymSessionService gymSessionService, stats estadisticasClient, ReservationService reservationService, PhysicalProgressService physicalProgressService) {
        this.gymSessionService = gymSessionService;
        this.reservationService = reservationService;
        this.estadisticasClient = estadisticasClient;
        this.physicalProgressService = physicalProgressService;
    }

    @GetMapping("/session-statistics")
    @Operation(summary = "Obtener estadísticas de sesiones por entrenador")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSessionStatisticsByTrainer() {
        List<GymSession> sessions = gymSessionService.getAllGymSessions();

        Map<String, Integer> sesionesPorCoach = sessions.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getCoachId().getId(),
                        Collectors.reducing(0, e -> 1, Integer::sum)
                ));

        Map<String, Object> estadisticas = estadisticasClient.getStatsByCoach(sesionesPorCoach);

        return ResponseEntity.ok(new ApiResponse<>(true, "Estadísticas obtenidas correctamente", estadisticas));


    }

    @GetMapping("/students-by-session")
    @Operation(summary = "Cantidad de estudiantes por sesión")
    public ResponseEntity<ApiResponse<Map<String, Integer>>> getStudentsBySession() {
        List<Reservation> reservations = reservationService.getAllReservations();

        Map<String, Integer> studentCountBySession = reservations.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getGymSessionId().getId(),
                        Collectors.reducing(0, e -> 1, Integer::sum)
                ));

        return ResponseEntity.ok(new ApiResponse<>(true, "Conteo por sesión generado", studentCountBySession));
    }
    @GetMapping("/physical-progress/user/{userId}")
    @Operation(summary = "Obtener estadísticas de progreso físico por usuario")
    public ResponseEntity<ApiResponse<List<PhysicalProgressDTO>>> getPhysicalProgressByUser(@PathVariable String userId) {
        User user = new User();
        user.setId(userId); // solo necesitas el ID para la búsqueda

        List<PhysicalProgress> progressList = physicalProgressService.getPhysicalProgressByUserId(user);

        List<PhysicalProgressDTO> progressDTOs = progressList.stream()
                .sorted(Comparator.comparing(PhysicalProgress::getRegistrationDate))
                .map(progress -> {
                    PhysicalProgressDTO dto = new PhysicalProgressDTO();
                    dto.setId(progress.getId());
                    dto.setRegistrationDate(progress.getRegistrationDate());
                    dto.setWeight(progress.getWeight());
                    dto.setHeight(progress.getHeight());
                    dto.setWaists(progress.getWaists());
                    dto.setChest(progress.getChest());
                    dto.setRightarm(progress.getRightarm());
                    dto.setLeftarm(progress.getLeftarm());
                    dto.setRightleg(progress.getRightleg());
                    dto.setLeftleg(progress.getLeftleg());
                    return dto;
                }).toList();

        return ResponseEntity.ok(new ApiResponse<>(true, "Progreso físico obtenido correctamente", progressDTOs));
    }
}
