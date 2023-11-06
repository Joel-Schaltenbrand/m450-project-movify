package ch.bbzbl.movify.service;

import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
class MovieSearchTest {
	@InjectMocks
	private MovieServiceImpl movieService;

	@Test
	void searchMovies() throws IOException {
		String movies = IOUtils.toString(Objects.requireNonNull(MovieSearchTest.class.getResourceAsStream("/searchResult.json")), StandardCharsets.UTF_8);
		Mockito.when(movieService.doGet(Mockito.anyString(), Mockito.any())).thenReturn(Optional.of(movies));
		Mockito.when(movieService.getMovieDetails(597)).thenReturn(createFakeMovieExtended("Titanic", "1997"));
		Mockito.when(movieService.getMovieDetails(16535)).thenReturn(createFakeMovieExtended("Titanic 2", "1953"));

		List<MovieExtended> moviesExtended = movieService.searchMovie("Titanic");
		Assertions.assertEquals(2, moviesExtended.size());
		Assertions.assertEquals("Titanic", moviesExtended.get(0).getTitle());
		Assertions.assertEquals("Titanic 2", moviesExtended.get(1).getTitle());
		Assertions.assertEquals("1997", moviesExtended.get(0).getReleaseYear());
		Assertions.assertEquals("1953", moviesExtended.get(1).getReleaseYear());
	}

	@Test
	void searchMoviesNoResults() {
		String emptyResult = "[]";
		Mockito.when(movieService.doGet(Mockito.anyString(), Mockito.any())).thenReturn(Optional.of(emptyResult));
		List<MovieExtended> moviesExtended = movieService.searchMovie("NonExistentMovie");
		Assertions.assertTrue(moviesExtended.isEmpty());
	}

	private MovieExtended createFakeMovieExtended(String name, String year) {
		MovieExtended movieExtended = new MovieExtended();
		movieExtended.setTitle(name);
		movieExtended.setReleaseYear(year);
		return movieExtended;
	}

}
