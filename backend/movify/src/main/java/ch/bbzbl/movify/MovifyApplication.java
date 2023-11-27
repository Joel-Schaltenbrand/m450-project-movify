package ch.bbzbl.movify;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;

@SpringBootApplication
@EnableMongoRepositories
public class MovifyApplication implements CommandLineRunner {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(MovifyApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			mongoTemplate.getDb().runCommand(new Document("ping", 1));
			System.out.println("OK - Verbindung zu MongoDB erfolgreich.");
		} catch (Exception e) {
			System.err.println("Fehler beim Verbinden zu MongoDB: " + e.getMessage());
			System.exit(1);
		}
	}
}
