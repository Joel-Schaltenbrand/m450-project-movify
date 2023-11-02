package ch.bbzbl.movify.model.movie.cast;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cast {
	@SerializedName("character")
	private String character;
	@SerializedName("name")
	private String name;
	@SerializedName("profile_path")
	private String profilePath;
}
