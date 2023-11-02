package ch.bbzbl.movify.service;

import ch.bbzbl.movify.model.movie.movie.MovieExtended;

public interface MovieService {
	MovieExtended getRandomMovie();

	MovieExtended getMovieDetails(long id);
}
