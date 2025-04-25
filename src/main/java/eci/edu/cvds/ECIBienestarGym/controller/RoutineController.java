package eci.edu.cvds.ECIBienestarGym.controller;

import eci.edu.cvds.ECIBienestarGym.service.RoutineService;
import org.junit.jupiter.api.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag("Routine")
public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {

        this.routineService = routineService;
    }
}
