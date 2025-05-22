package edu.eci.cvds.ECIBienestarGym.statistic;

import edu.eci.cvds.ECIBienestarGym.dto.PhysicalProgressDTO;
import edu.eci.cvds.ECIBienestarGym.dto.ReservationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "estadisticas-service", url = "") /*http://localhost:8081/api/stats*/
public interface stats {
    @PostMapping("/sessions/by-coach")
    Map<String, Object> getStatsByCoach(@RequestBody Map<String, Integer> sessionsByCoach);

    @PostMapping("/students/by-session")
    Map<String, Integer> getStudentsBySession(@RequestBody List<ReservationDTO> reservations);

    @GetMapping("/trainer/physical-progress/user/{userId}")
    List<PhysicalProgressDTO> getPhysicalProgressByUser(@PathVariable("userId") String userId);

}
