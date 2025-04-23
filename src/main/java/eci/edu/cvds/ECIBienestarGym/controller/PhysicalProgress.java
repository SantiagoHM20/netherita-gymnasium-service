package eci.edu.cvds.ECIBienestarGym.controller;

import eci.edu.cvds.ECIBienestarGym.service.PhysicalProgressService;
import org.junit.jupiter.api.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag("PhysicalProgress")
public class PhysicalProgress {

    private final PhysicalProgressService physicalProgressService;

    public PhysicalProgress(PhysicalProgressService physicalProgressService) {
        this.physicalProgressService = physicalProgressService;
    }
}
