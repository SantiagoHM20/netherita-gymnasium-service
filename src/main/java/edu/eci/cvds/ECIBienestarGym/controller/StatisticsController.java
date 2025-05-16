package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.model.ApiResponse;
import edu.eci.cvds.ECIBienestarGym.model.GymSession;
import edu.eci.cvds.ECIBienestarGym.service.GymSessionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/trainer")
@PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
public class StatisticsController {


        private final GymSessionService gymSessionService;
        private final RestTemplate restTemplate;

        public StatisticsController(GymSessionService gymSessionService, RestTemplate restTemplate) {
            this.gymSessionService = gymSessionService;
            this.restTemplate = restTemplate;
        }

        @GetMapping("/session-statistics")
        @Operation(summary = "Obtener estadísticas de sesiones por entrenador")
        public ResponseEntity<ApiResponse<Object>> getSessionStatisticsByTrainer() {
            List<GymSession> sessions = gymSessionService.getAllGymSessions();

            String url = "Endpoint de Jesus como url HTTP"; /*http://estadisticas-service/api/stats/sessions/by-coach*/
            HttpEntity<List<GymSession>> request = new HttpEntity<>(sessions);

            ResponseEntity<Object> response = restTemplate.postForEntity(url, request, Object.class);

            return ResponseEntity.ok(new ApiResponse<>(true, "Estadísticas obtenidas correctamente", response.getBody()));
        }
    }
