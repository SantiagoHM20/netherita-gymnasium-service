package eci.edu.cvds.ECIBienestarGym.controller;

import eci.edu.cvds.ECIBienestarGym.service.GymSessionService;
import org.junit.jupiter.api.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag("GymSession")
public class GymSessionController {

    private final GymSessionService gymSessionService;

    public GymSessionController(GymSessionService gymSessionService) {

        this.gymSessionService = gymSessionService;
    }
}
