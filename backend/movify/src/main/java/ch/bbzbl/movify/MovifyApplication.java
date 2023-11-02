package ch.bbzbl.movify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MovifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovifyApplication.class, args);
	}

}
