package edu.eci.cvds.ECIBienestarGym.service;

import edu.eci.cvds.ECIBienestarGym.dto.ExerciseDTO;
import edu.eci.cvds.ECIBienestarGym.dto.PhysicalProgressDTO;
import edu.eci.cvds.ECIBienestarGym.dto.RoutineDTO;
import edu.eci.cvds.ECIBienestarGym.dto.UserDTO;
import edu.eci.cvds.ECIBienestarGym.embeddables.Exercise;
import edu.eci.cvds.ECIBienestarGym.model.PhysicalProgress;
import edu.eci.cvds.ECIBienestarGym.model.Routine;
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

    public List<PhysicalProgress> getPhysicalProgressByUserIdAndDate(User userId, LocalDate date) {
        return physicalProgressRepository.findByUserIdAndRegistrationDate(userId, date);
    }
    public List<PhysicalProgress> getPhysicalProgressByUserIdAndDateBetween(User userId, LocalDate startDate, LocalDate endDate) {
        return physicalProgressRepository.findByUserIdAndRegistrationDateBetween(userId, startDate, endDate);
    }
    public PhysicalProgress createPhysicalProgress(PhysicalProgressDTO physicalProgressDTO) {
        PhysicalProgress progress = mapToPhysicalProgress(physicalProgressDTO);
        return physicalProgressRepository.save(progress);
    }

    public PhysicalProgress updatePhysicalProgress(String id, PhysicalProgressDTO physicalProgressDTO) {
        PhysicalProgress progress = physicalProgressRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el progreso físico"));
        PhysicalProgress updatedProgress = mapToPhysicalProgress(physicalProgressDTO);
        updatedProgress.setId(progress.getId());
        return physicalProgressRepository.save(updatedProgress);
    }

    public void deletePhysicalProgress(String id) {
        PhysicalProgress progress = physicalProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PhysicalProgress not found with id: " + id));
        physicalProgressRepository.deleteById(id);
    }

    private PhysicalProgress mapToPhysicalProgress(PhysicalProgressDTO physicalProgressDTO) {
        PhysicalProgress physicalProgress = new PhysicalProgress();
        physicalProgress.setUserId(mapToUser(physicalProgressDTO.getUserId()));
        physicalProgress.setRoutine(mapToRoutine(physicalProgressDTO.getRoutine())); // Mapeo de RoutineDTO a Routine
        physicalProgress.setGoal(physicalProgressDTO.getGoal());
        physicalProgress.setRegistrationDate(physicalProgressDTO.getRegistrationDate());
        physicalProgress.setWeight(physicalProgressDTO.getWeight());
        physicalProgress.setHeight(physicalProgressDTO.getHeight());
        physicalProgress.setWaists(physicalProgressDTO.getWaists());
        physicalProgress.setChest(physicalProgressDTO.getChest());
        physicalProgress.setArms(physicalProgressDTO.getArms());
        physicalProgress.setLegs(physicalProgressDTO.getLegs());
        physicalProgress.setShoulders(physicalProgressDTO.getShoulders());

        return physicalProgress;
    }

    private User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }

    private Routine mapToRoutine(RoutineDTO routineDTO) {
        Routine routine = new Routine();
        routine.setId(routineDTO.getId());
        routine.setName(routineDTO.getName());
        routine.setDescription(routineDTO.getDescription());
        routine.setDifficulty(routineDTO.getDifficulty());
        routine.setExercises(routineDTO.getExercises().stream()
            .map(this::mapToExercise)
            .toList());
        return routine;
    }

    private Exercise mapToExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDTO.getName());
        exercise.setRepetitions(exerciseDTO.getRepetitions());
        exercise.setSets(exerciseDTO.getSets());
        exercise.setDuration(exerciseDTO.getDuration());
        return exercise;
    }


}
