package ch.bbzbl.movify.model.movie.movie;

import ch.bbzbl.movify.model.movie.genre.Genres;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {
	@SerializedName("id")
	private Long id;

	@SerializedName("title")
	private String title;

	@SerializedName("genres")
	private List<Genres> genres;

	@SerializedName("runtime")
	private int runtime;

	@SerializedName("overview")
	private String overview;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("release_date")
	private String releaseDate;
}
