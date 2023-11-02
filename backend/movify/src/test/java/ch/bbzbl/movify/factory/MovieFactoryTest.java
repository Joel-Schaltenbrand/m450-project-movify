package ch.bbzbl.movify.factory;

import ch.bbzbl.movify.model.movie.cast.Cast;
import ch.bbzbl.movify.model.movie.cast.CastModified;
import ch.bbzbl.movify.model.movie.genre.Genres;
import ch.bbzbl.movify.model.movie.movie.Movie;
import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class MovieFactoryTest {

	@InjectMocks
	private MovieFactory movieFactory;

	@Mock
	private Movie movie;

	@Mock
	private Cast cast;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateMovieExtended_WithTrailer() {
		prepareSampleMovie();
		List<CastModified> castList = prepareSampleCastList();
		Optional<String> movieTrailer = Optional.of("sample-trailer-id");

		MovieExtended movieExtended = movieFactory.createMovieExtended(movie, castList, movieTrailer);

		Assertions.assertEquals(1L, movieExtended.getId());
		Assertions.assertEquals("Sample Movie", movieExtended.getTitle());
		assertGenres(movieExtended, "Action", "Drama");
		Assertions.assertEquals(120, movieExtended.getRuntime());
		Assertions.assertEquals("This is a sample movie.", movieExtended.getOverview());
		Assertions.assertEquals("https://image.tmdb.org/t/p/original/sample-poster.jpg", movieExtended.getPosterPath());
		Assertions.assertEquals(castList, movieExtended.getCast());
		Assertions.assertEquals("2023", movieExtended.getReleaseYear());
		Assertions.assertEquals("https://www.youtube.com/watch?v=sample-trailer-id", movieExtended.getTrailer());
	}

	@Test
	public void testCreateMovieExtended_WithoutTrailer() {
		prepareAnotherMovie();
		List<CastModified> castList = List.of();
		Optional<String> movieTrailer = Optional.empty();

		MovieExtended movieExtended = movieFactory.createMovieExtended(movie, castList, movieTrailer);

		Assertions.assertEquals(2L, movieExtended.getId());
		Assertions.assertEquals("Another Movie", movieExtended.getTitle());
		assertGenres(movieExtended, "Comedy");
		Assertions.assertEquals(90, movieExtended.getRuntime());
		Assertions.assertEquals("This is another movie.", movieExtended.getOverview());
		Assertions.assertEquals("https://image.tmdb.org/t/p/original/another-poster.jpg", movieExtended.getPosterPath());
		Assertions.assertEquals(castList, movieExtended.getCast());
		Assertions.assertEquals("2023", movieExtended.getReleaseYear());
		Assertions.assertNull(movieExtended.getTrailer());
	}

	@Test
	public void testCreateCastExtended() {
		prepareSampleActor();
		List<Cast> castList = List.of(cast);

		List<CastModified> castModifiedList = movieFactory.createCastExtended(castList);

		Assertions.assertEquals(1, castModifiedList.size());
		Assertions.assertEquals("Sample Actor", castModifiedList.get(0).getName());
		Assertions.assertEquals("Sample Character", castModifiedList.get(0).getCharacter());
		Assertions.assertEquals("https://image.tmdb.org/t/p/original/sample-actor.jpg", castModifiedList.get(0).getProfilePhoto());
	}

	@Test
	public void testCreateCastExtended_EmptyList() {
		List<Cast> castList = List.of();

		List<CastModified> castModifiedList = movieFactory.createCastExtended(castList);

		Assertions.assertTrue(castModifiedList.isEmpty());
	}

	private void prepareSampleMovie() {
		Mockito.when(movie.getId()).thenReturn(1L);
		Mockito.when(movie.getTitle()).thenReturn("Sample Movie");
		Mockito.when(movie.getReleaseDate()).thenReturn("2023-01-01");
		List<Genres> genresList = new ArrayList<>();
		genresList.add(prepareGenre("Action"));
		genresList.add(prepareGenre("Drama"));
		Mockito.when(movie.getGenres()).thenReturn(genresList);
		Mockito.when(movie.getRuntime()).thenReturn(120);
		Mockito.when(movie.getOverview()).thenReturn("This is a sample movie.");
		Mockito.when(movie.getPosterPath()).thenReturn("/sample-poster.jpg");
	}

	private void prepareAnotherMovie() {
		Mockito.when(movie.getId()).thenReturn(2L);
		Mockito.when(movie.getTitle()).thenReturn("Another Movie");
		Mockito.when(movie.getReleaseDate()).thenReturn("2023-01-01");
		List<Genres> genresList = new ArrayList<>();
		genresList.add(prepareGenre("Comedy"));
		Mockito.when(movie.getGenres()).thenReturn(genresList);
		Mockito.when(movie.getRuntime()).thenReturn(90);
		Mockito.when(movie.getOverview()).thenReturn("This is another movie.");
		Mockito.when(movie.getPosterPath()).thenReturn("/another-poster.jpg");
	}

	private List<CastModified> prepareSampleCastList() {
		CastModified castModified = new CastModified();
		castModified.setName("Sample Actor");
		castModified.setCharacter("Sample Character");
		castModified.setProfilePhoto("/sample-actor.jpg");
		return List.of(castModified);
	}

	private Genres prepareGenre(String name) {
		Genres genre = new Genres();
		genre.setName(name);
		return genre;
	}

	private void assertGenres(MovieExtended movieExtended, String... genreNames) {
		Assertions.assertEquals(genreNames.length, movieExtended.getGenres().size());
		for (int i = 0; i < genreNames.length; i++) {
			Assertions.assertEquals(genreNames[i], movieExtended.getGenres().get(i).getName());
		}
	}

	private void prepareSampleActor() {
		Mockito.when(cast.getName()).thenReturn("Sample Actor");
		Mockito.when(cast.getCharacter()).thenReturn("Sample Character");
		Mockito.when(cast.getProfilePath()).thenReturn("/sample-actor.jpg");
	}
}
