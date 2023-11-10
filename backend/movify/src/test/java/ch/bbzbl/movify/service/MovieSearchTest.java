package ch.bbzbl.movify.service;

import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <b>TDD Ziel:</b> Suche nach einem Film anhand eines Suchbegriffs über einen REST-Endpunkt.<br>
 * Es sollte eine Liste mit gefetchten Filmen zurückgegeben werden.
 * <p>
 * Im MovieService:<br>
 * <ol>
 *     <li>Abrufen von <a href="https://api.themoviedb.org/3/search/movie">API</a> mit den erforderlichen Parametern. (API-Key, Suchbegriff, Sprache, Adult-Off [Analog anderen Aufrufen], <b>WICHTIG</b> Path: search/movie)</li>
 *     <li>Umwandeln in TopRatedMovie-Objekte Liste (über TopMovieResults) mit GSON. (Analog anderen Fetchings)</li>
 *     <li>Über while-Schlaufe (topRatedMovieListe.getId) Details für jeden Film mit getDetails() abrufen und als Liste<MovieExtended> speichern.</li>
 *     <li>Liste mit den Extended Movies an Controller zurückgeben</li>
 * </ol>
 * Es muss kein vorhandener Code umgeschrieben oder verändert werden. Es werden jegentlich neue Methoden erstellt, welche bereits vorhandene Methoden aufrufen.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MovieSearchTest {
	@Autowired
	private MovieServiceImpl movieService;

	@Test
	void searchMovies() {
		List<MovieExtended> moviesExtended = movieService.searchMovie("Titanic");
		Assertions.assertFalse(moviesExtended.isEmpty());
		int numberOfMovies = moviesExtended.size();
		Assertions.assertTrue(numberOfMovies >= 1 && numberOfMovies <= 20);
	}

	@Test
	void searchMovie() {
		List<MovieExtended> moviesExtended = movieService.searchMovie("Avatar: The Way of Water");
		Assertions.assertFalse(moviesExtended.isEmpty());
		int numberOfMovies = moviesExtended.size();
		Assertions.assertEquals(1, numberOfMovies);
		Assertions.assertEquals("Avatar: The Way of Water", moviesExtended.get(0).getTitle());
		Assertions.assertEquals(76600, moviesExtended.get(0).getId());
		Assertions.assertEquals("2022", moviesExtended.get(0).getReleaseYear());
	}


	@Test
	void searchMoviesNoResults() {
		List<MovieExtended> moviesExtended = movieService.searchMovie("NonExistentMovie");
		Assertions.assertTrue(moviesExtended.isEmpty());
	}
}
