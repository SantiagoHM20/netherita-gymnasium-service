package edu.eci.cvds.ECIBienestarGym.controller;

import edu.eci.cvds.ECIBienestarGym.model.PhysicalProgress;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.service.PhysicalProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/physical-progress")
@RequiredArgsConstructor
@Tag(name = "Physical Progress", description = "Operaciones relacionadas con el progreso físico")
public class PhysicalProgressController {

    private final PhysicalProgressService physicalProgressService;

    @GetMapping
    @Operation(summary = "Obtener todos los registros de progreso físico")
    public List<PhysicalProgress> getAllPhysicalProgress() {
        return physicalProgressService.getAllPhysicalProgress();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un registro de progreso físico por su ID")
    public PhysicalProgress getPhysicalProgressById(@PathVariable String id) {
        return physicalProgressService.getPhysicalProgressById(id);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener registros de progreso físico por ID de usuario")
    public List<PhysicalProgress> getPhysicalProgressByUserId(@PathVariable String userId) {
        User user = new User();
        user.setId(userId);
        return physicalProgressService.getPhysicalProgressByUserId(user);
    }

    @GetMapping("/date")
    @Operation(summary = "Obtener registros de progreso físico por fecha de registro")
    public List<PhysicalProgress> getPhysicalProgressByRegistrationDate(@RequestParam("registrationDate") String registrationDate) {
        LocalDate date = LocalDate.parse(registrationDate);
        return physicalProgressService.getPhysicalProgressByRegistrationDate(date);
    }
}