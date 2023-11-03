package ch.bbzbl.movify.service;

import ch.bbzbl.movify.constants.SessionConstants;
import ch.bbzbl.movify.factory.UserFactory;
import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import ch.bbzbl.movify.model.user.User;
import ch.bbzbl.movify.model.user.UserDTO;
import ch.bbzbl.movify.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserFactory userFactory;

	@Autowired
	private MovieService movieService;

	@Autowired
	private HttpSession session;

	@Override
	public UserDTO createUser(String email, String password) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new IllegalArgumentException("User already exists");
		}
		if (password.length() < 8 || !password.matches(".*[A-Z].*")
				|| !password.matches(".*[a-z].*") || !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
			throw new IllegalArgumentException("Password rules wrong");
		}
		User user = userRepository.save(new User(UUID.randomUUID().toString(), email, BCrypt.hashpw(password, BCrypt.gensalt()), Collections.emptyList()));
		session.setAttribute(SessionConstants.USER_ID, user.getId());
		return userFactory.createUserDTO(user);
	}

	@Override
	public UserDTO loginUser(String email, String password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));
		if (!BCrypt.checkpw(password, user.getPassword())) {
			throw new IllegalArgumentException("Wrong password");
		}
		session.setAttribute(SessionConstants.USER_ID, user.getId());
		return userFactory.createUserDTO(user);
	}

	@Override
	public UserDTO getFavoriteMovies(String id) {
		return userFactory.createUserDTO(getUserById(id));
	}

	@Override
	public UserDTO addFavorite(String id, int movieId) {
		User user = getUserById(id);
		MovieExtended movie = movieService.getMovieDetails(movieId);
		List<MovieExtended> favoriteMovies = Optional.ofNullable(user.getFavoriteMovies()).orElse(new ArrayList<>());
		if (favoriteMovies.stream().anyMatch(favoriteMovie -> favoriteMovie.getId() == movieId)) {
			throw new IllegalArgumentException("Movie already in favorites");
		}
		favoriteMovies.add(movie);
		user.setFavoriteMovies(favoriteMovies);
		return userFactory.createUserDTO(userRepository.save(user));
	}

	@Override
	public UserDTO removeFavorite(String id, int movieId) {
		User user = getUserById(id);
		List<MovieExtended> favoriteMovies = Optional.ofNullable(user.getFavoriteMovies()).orElse(new ArrayList<>());
		if (favoriteMovies.stream().noneMatch(movie -> movie.getId() == movieId)) {
			throw new IllegalArgumentException("Movie not in favorites");
		}
		favoriteMovies.removeIf(movie -> movie.getId() == movieId);
		user.setFavoriteMovies(favoriteMovies);
		return userFactory.createUserDTO(userRepository.save(user));
	}

	@Override
	public UserDTO logoutUser(String id) {
		User user = getUserById(id);
		session.removeAttribute(SessionConstants.USER_ID);
		return userFactory.createUserDTO(user);
	}

	private User getUserById(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		if (!checkLoggedIn(id)) {
			throw new IllegalArgumentException("SECURITYFAILED");
		}
		return user;
	}

	boolean checkLoggedIn(String id) {
		String uuid = (String) session.getAttribute(SessionConstants.USER_ID);
		return uuid != null && uuid.equals(id);
	}
}
