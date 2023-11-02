package ch.bbzbl.movify.model.user;

import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	private String id;
	private String email;
	private List<MovieExtended> favoriteMovies;
}
