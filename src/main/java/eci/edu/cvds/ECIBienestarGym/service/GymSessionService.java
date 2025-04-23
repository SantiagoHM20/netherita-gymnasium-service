package eci.edu.cvds.ECIBienestarGym.service;

import eci.edu.cvds.ECIBienestarGym.repository.GymSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class GymSessionService {
    private final GymSessionRepository gymSessionRepository;

    public GymSessionService(GymSessionRepository gymSessionRepository) {
        this.gymSessionRepository = gymSessionRepository;
    }
}
