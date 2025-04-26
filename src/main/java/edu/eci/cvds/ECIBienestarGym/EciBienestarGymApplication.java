package edu.eci.cvds.ECIBienestarGym;

import org.springframework.aot.generate.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
@Generated
public class EciBienestarGymApplication {
	public static void main(String[] args) {
		SpringApplication.run(EciBienestarGymApplication.class, args);
	}
}
