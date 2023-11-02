package ch.bbzbl.movify.rest;

import ch.bbzbl.movify.model.user.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

public interface UserController {

	@CrossOrigin(originPatterns = "*localhost*", allowCredentials = "true")
	@PostMapping(value = "/login", produces = {"application/json"})
	ResponseEntity<UserDTO> loginUser(String email, String password);

	@CrossOrigin(originPatterns = "*localhost*", allowCredentials = "true")
	@PostMapping(value = "/logout", produces = {"application/json"})
	ResponseEntity<UserDTO> logoutUser(String id);

	@CrossOrigin(originPatterns = "*localhost*", allowCredentials = "true")
	@PostMapping(value = "/register", produces = {"application/json"})
	ResponseEntity<UserDTO> createUser(String email, String password);

	@CrossOrigin(originPatterns = "*localhost*", allowCredentials = "true")
	@PostMapping(value = "/getFavoriteMovies", produces = {"application/json"})
	ResponseEntity<UserDTO> getFavoriteMovies(String id);

	@CrossOrigin(originPatterns = "*localhost*", allowCredentials = "true")
	@PostMapping(value = "/addFavorite", produces = {"application/json"})
	ResponseEntity<UserDTO> addFavorite(String id, int movieId);

	@CrossOrigin(originPatterns = "*localhost*", allowCredentials = "true")
	@PostMapping(value = "/removeFavorite", produces = {"application/json"})
	ResponseEntity<UserDTO> removeFavorite(String id, int movieId);
}
