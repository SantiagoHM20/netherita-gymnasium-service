package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.model.PhysicalProgress;
import edu.eci.cvds.ECIBienestarGym.model.User;
import edu.eci.cvds.ECIBienestarGym.repository.PhysicalProgressRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PhysicalProgressService {
    private final PhysicalProgressRepository physicalProgressRepository;

    public PhysicalProgressService(PhysicalProgressRepository physicalProgressRepository) {
        this.physicalProgressRepository = physicalProgressRepository;
    }

    public List<PhysicalProgress> getAllPhysicalProgress(){
        return physicalProgressRepository.findAll();
    }

    public PhysicalProgress getPhysicalProgressById(String id){
        return physicalProgressRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró el progreso Físico"));
    }

    public List<PhysicalProgress> getPhysicalProgressByUserId(User userId){
        return physicalProgressRepository.findByUserId(userId);
    }

    public List<PhysicalProgress> getPhysicalProgressByRegistrationDate(LocalDate registrationDate){
        return physicalProgressRepository.findByRegistrationDate(registrationDate);
    }

}
