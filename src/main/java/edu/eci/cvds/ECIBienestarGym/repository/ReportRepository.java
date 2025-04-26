package edu.eci.cvds.ECIBienestarGym.repository;


import edu.eci.cvds.ECIBienestarGym.enums.ReportType;
import edu.eci.cvds.ECIBienestarGym.model.Report;
import edu.eci.cvds.ECIBienestarGym.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    


    List<Report> findByCoachId(User coachId);
    
    List<Report> findByGeneratedAt(LocalDate generatedAt);

    List<Report> findByType(ReportType type);

}
