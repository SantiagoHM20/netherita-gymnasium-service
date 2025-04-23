package eci.edu.cvds.ECIBienestarGym.repository;


import eci.edu.cvds.ECIBienestarGym.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
}
