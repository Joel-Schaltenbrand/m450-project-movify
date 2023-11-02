package ch.bbzbl.movify.factory;

import ch.bbzbl.movify.model.movie.cast.Cast;
import ch.bbzbl.movify.model.movie.cast.CastModified;
import ch.bbzbl.movify.model.movie.movie.Movie;
import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieFactory {
	public MovieExtended createMovieExtended(Movie movie, List<CastModified> cast, Optional<String> movieTrailer) {
		MovieExtended movieExtended = new MovieExtended();
		movieExtended.setId(movie.getId());
		movieExtended.setTitle(movie.getTitle());
		movieExtended.setGenres(movie.getGenres());
		movieExtended.setRuntime(movie.getRuntime());
		movieExtended.setOverview(movie.getOverview());
		movieExtended.setPosterPath(String.format("https://image.tmdb.org/t/p/original%s", movie.getPosterPath()));
		movieExtended.setCast(cast);
		movieExtended.setReleaseYear(movie.getReleaseDate().substring(0, 4));
		movieExtended.setPosterPath(String.format("https://image.tmdb.org/t/p/original%s", movie.getPosterPath()));
		movieTrailer.ifPresent(s -> movieExtended.setTrailer(String.format("https://www.youtube.com/watch?v=%s", s)));
		return movieExtended;
	}

	public List<CastModified> createCastExtended(List<Cast> cast) {
		List<CastModified> castModifiedList = new ArrayList<>();
		for (Cast castMember : cast) {
			CastModified castModified = new CastModified();
			castModified.setName(castMember.getName());
			castModified.setCharacter(castMember.getCharacter());
			castModified.setProfilePhoto(String.format("https://image.tmdb.org/t/p/original%s", castMember.getProfilePath()));
			castModifiedList.add(castModified);
		}
		return castModifiedList;
	}
}
