package eci.edu.cvds.ECIBienestarGym.service;

import eci.edu.cvds.ECIBienestarGym.repository.RoutineRepository;
import org.springframework.stereotype.Service;

@Service
public class RoutineService {
    private final RoutineRepository routineRepository;

    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }
}
