package ch.bbzbl.movify.model.movie.movie;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TopRatedMovie {
	@SerializedName("id")
	private long id;
	@SerializedName("overview")
	private String overview;
}
