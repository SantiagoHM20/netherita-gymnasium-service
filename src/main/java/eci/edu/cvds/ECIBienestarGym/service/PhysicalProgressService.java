package eci.edu.cvds.ECIBienestarGym.service;

import eci.edu.cvds.ECIBienestarGym.repository.PhysicalProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class PhysicalProgressService {
    private final PhysicalProgressRepository physicalProgressRepository;

    public PhysicalProgressService(PhysicalProgressRepository physicalProgressRepository) {
        this.physicalProgressRepository = physicalProgressRepository;
    }
}
