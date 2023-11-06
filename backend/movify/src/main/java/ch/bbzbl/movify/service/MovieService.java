package ch.bbzbl.movify.service;

import ch.bbzbl.movify.model.movie.movie.MovieExtended;

import java.util.List;

public interface MovieService {
	MovieExtended getRandomMovie();

	MovieExtended getMovieDetails(long id);

	List<MovieExtended> searchMovie(String search);
}
