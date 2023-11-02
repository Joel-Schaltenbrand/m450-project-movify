package ch.bbzbl.movify.rest;

import ch.bbzbl.movify.model.user.UserDTO;
import ch.bbzbl.movify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserControllerImpl implements UserController {

	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<UserDTO> createUser(String email, String password) {
		try {
			return new ResponseEntity<>(userService.createUser(email, password), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return checkExeptions(e);
		}
	}

	@Override
	public ResponseEntity<UserDTO> loginUser(String email, String password) {
		try {
			return new ResponseEntity<>(userService.loginUser(email, password), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return checkExeptions(e);
		}
	}

	@Override
	public ResponseEntity<UserDTO> logoutUser(String id) {
		try {
			return new ResponseEntity<>(userService.logoutUser(id), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return checkExeptions(e);
		}
	}

	@Override
	public ResponseEntity<UserDTO> getFavoriteMovies(String id) {
		try {
			return new ResponseEntity<>(userService.getFavoriteMovies(id), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return checkExeptions(e);
		}
	}

	@Override
	public ResponseEntity<UserDTO> addFavorite(String id, int movieId) {
		try {
			return new ResponseEntity<>(userService.addFavorite(id, movieId), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return checkExeptions(e);
		}
	}

	@Override
	public ResponseEntity<UserDTO> removeFavorite(String id, int movieId) {
		try {
			return new ResponseEntity<>(userService.removeFavorite(id, movieId), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return checkExeptions(e);
		}
	}

	private ResponseEntity<UserDTO> checkExeptions(IllegalArgumentException e) {
		return switch (e.getMessage()) {
			case "User already exists", "Movie already in favorites" -> new ResponseEntity<>(HttpStatus.CONFLICT);
			case "Password rules wrong" -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			case "User not found", "Movie not in favorites" -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
			case "SECURITYFAILED", "Wrong password" -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		};
	}
}
