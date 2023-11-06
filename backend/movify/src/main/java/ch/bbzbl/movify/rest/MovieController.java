package ch.bbzbl.movify.rest;

import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface MovieController {

	@CrossOrigin(originPatterns = "*localhost*", allowCredentials = "true")
	@GetMapping(value = "/getRandomMovie", produces = {"application/json"})
	ResponseEntity<MovieExtended> getRandomMovie();

	@CrossOrigin(originPatterns = "*localhost*", allowCredentials = "true")
	@GetMapping(value = "/searchMovie", produces = {"application/json"})
	ResponseEntity<List<MovieExtended>> searchMovie(String search);
}
