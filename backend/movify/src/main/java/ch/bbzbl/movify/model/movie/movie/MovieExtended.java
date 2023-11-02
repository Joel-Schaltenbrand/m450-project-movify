package ch.bbzbl.movify.model.movie.movie;

import ch.bbzbl.movify.model.movie.cast.CastModified;
import ch.bbzbl.movify.model.movie.genre.Genres;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieExtended {
	private Long id;
	private String title;
	private List<Genres> genres;
	private int runtime;
	private String overview;
	private String posterPath;
	private String releaseYear;
	private String trailer;
	private List<CastModified> cast;
}
