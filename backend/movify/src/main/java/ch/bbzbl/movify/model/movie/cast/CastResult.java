package ch.bbzbl.movify.model.movie.cast;

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
public class CastResult {
	@SerializedName("cast")
	private List<Cast> cast;
}
