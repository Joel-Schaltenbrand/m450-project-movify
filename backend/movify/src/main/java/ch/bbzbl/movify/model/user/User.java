package ch.bbzbl.movify.model.user;

import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
	private String id;
	private String email;
	private String password;
	private List<MovieExtended> favoriteMovies = new ArrayList<>();
}
