package eci.edu.cvds.ECIBienestarGym.controller;

import eci.edu.cvds.ECIBienestarGym.service.GymSessionService;
import org.junit.jupiter.api.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag("Session")
public class SessionController {

    private final GymSessionService sessionService;

    public SessionController(GymSessionService sessionService) {
        this.sessionService = sessionService;
    }
}
