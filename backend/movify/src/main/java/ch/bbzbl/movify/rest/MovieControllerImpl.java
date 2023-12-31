package ch.bbzbl.movify.rest;

import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import ch.bbzbl.movify.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieControllerImpl implements MovieController {

	@Autowired
	private MovieService movieService;

	@Override
	public ResponseEntity<MovieExtended> getRandomMovie() {
		return new ResponseEntity<>(movieService.getRandomMovie(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<MovieExtended>> searchMovie(String search) {
		return new ResponseEntity<>(movieService.searchMovie(search), HttpStatus.OK);
	}
}
