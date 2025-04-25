package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.enums.ReportType;
import eci.edu.cvds.ECIBienestarGym.model.Report;
import eci.edu.cvds.ECIBienestarGym.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    
    List<Report> findAll();

    List<Report> findByCoachId(User coachId);
    
    List<Report> findByGeneratedAt(LocalDate generatedAt);

    List<Report> findByType(ReportType type);

}
