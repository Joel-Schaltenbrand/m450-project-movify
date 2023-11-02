package ch.bbzbl.movify.model.movie.trailer;

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
public class TrailerResult {
	@SerializedName("results")
	private List<Trailer> results;
}
