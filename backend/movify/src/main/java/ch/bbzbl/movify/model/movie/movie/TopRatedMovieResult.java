package ch.bbzbl.movify.model.movie.movie;

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
public class TopRatedMovieResult {
	@SerializedName("results")
	private List<TopRatedMovie> results;
}
