package ch.bbzbl.movify.service;

import ch.bbzbl.movify.constants.SessionConstants;
import ch.bbzbl.movify.factory.UserFactory;
import ch.bbzbl.movify.model.movie.movie.MovieExtended;
import ch.bbzbl.movify.model.user.User;
import ch.bbzbl.movify.model.user.UserDTO;
import ch.bbzbl.movify.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserFactory userFactory;

	@Mock
	private MovieService movieService;

	@Mock
	private HttpSession session;

	@Test
	void createUser_Success() {
		String email = "test@example.com";
		String password = "Passw0rd!";
		User user = new User("1", email, BCrypt.hashpw(password, BCrypt.gensalt()), Collections.emptyList());
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
		UserDTO userDTO = new UserDTO("1", email, Collections.emptyList());
		Mockito.when(userFactory.createUserDTO(user)).thenReturn(userDTO);

		UserDTO result = userService.createUser(email, password);

		Assertions.assertEquals(userDTO, result);
		Mockito.verify(session).setAttribute(SessionConstants.USER_ID, "1");
	}

	@Test
	void createUser_UserAlreadyExists() {
		String email = "test@example.com";
		String password = "Passw0rd!";
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

		Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUser(email, password));
		Mockito.verify(session, never()).setAttribute(anyString(), anyString());
	}

	@Test
	void createUser_InvalidPassword() {
		String email = "test@example.com";
		String password = "weak";

		Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUser(email, password));
		Mockito.verify(session, never()).setAttribute(anyString(), anyString());
	}

	@Test
	void loginUser_Success() {
		String email = "test@example.com";
		String password = "Passw0rd!";
		User user = new User("1", email, BCrypt.hashpw(password, BCrypt.gensalt()), Collections.emptyList());
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		UserDTO userDTO = new UserDTO("1", email, Collections.emptyList());
		Mockito.when(userFactory.createUserDTO(user)).thenReturn(userDTO);

		UserDTO result = userService.loginUser(email, password);

		Assertions.assertEquals(userDTO, result);
		Mockito.verify(session).setAttribute(SessionConstants.USER_ID, "1");
	}

	@Test
	void loginUser_UserNotFound() {
		String email = "test@example.com";
		String password = "Passw0rd!";
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

		Assertions.assertThrows(IllegalArgumentException.class, () -> userService.loginUser(email, password));
		Mockito.verify(session, never()).setAttribute(anyString(), anyString());
	}

	@Test
	void loginUser_WrongPassword() {
		String email = "test@example.com";
		String password = "WrongPassword";
		User user = new User("1", email, BCrypt.hashpw("Passw0rd!", BCrypt.gensalt()), Collections.emptyList());
		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		Assertions.assertThrows(IllegalArgumentException.class, () -> userService.loginUser(email, password));
		Mockito.verify(session, never()).setAttribute(anyString(), anyString());
	}

	@Test
	void getFavoriteMovies_Success() {
		String userId = "1";
		User user = new User(userId, "test@example.com", BCrypt.hashpw("Passw0rd!", BCrypt.gensalt()), Collections.emptyList());
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		UserDTO userDTO = new UserDTO(userId, "test@example.com", Collections.emptyList());
		Mockito.when(userFactory.createUserDTO(user)).thenReturn(userDTO);
		Mockito.when(session.getAttribute(SessionConstants.USER_ID)).thenReturn(userId);

		UserDTO result = userService.getFavoriteMovies(userId);

		Assertions.assertEquals(userDTO, result);
		Assertions.assertEquals(userId, result.getId());
	}

	@Test
	void addFavorite_Success() {
		String userId = "1";
		int movieId = 123;
		User user = new User(userId, "test@example.com", BCrypt.hashpw("Passw0rd!", BCrypt.gensalt()), new ArrayList<>());
		MovieExtended movie = new MovieExtended();
		movie.setId(1L);
		movie.setTitle("Test");
		movie.setOverview("Test");
		movie.setReleaseYear("2021");
		movie.setPosterPath("Test");
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(movieService.getMovieDetails(movieId)).thenReturn(movie);
		Mockito.when(userFactory.createUserDTO(user)).thenReturn(new UserDTO(userId, "test@example.com", Collections.singletonList(movie)));
		Mockito.when(session.getAttribute(SessionConstants.USER_ID)).thenReturn(userId);
		Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
		UserDTO result = userService.addFavorite(userId, movieId);

		Assertions.assertEquals("Test", result.getFavoriteMovies().get(0).getTitle());
		Assertions.assertEquals(1, result.getFavoriteMovies().size());
		Assertions.assertEquals("Test", result.getFavoriteMovies().get(0).getTitle());
		Assertions.assertEquals("Test", result.getFavoriteMovies().get(0).getOverview());
		Assertions.assertEquals("2021", result.getFavoriteMovies().get(0).getReleaseYear());
		Mockito.verify(userRepository).save(user);
	}

	@Test
	void addFavorite_MovieAlreadyInFavorites() {
		String userId = "1";
		int movieId = 123;
		User user = new User(userId, "test@example.com", BCrypt.hashpw("Passw0rd!", BCrypt.gensalt()), new ArrayList<>());
		MovieExtended movie = new MovieExtended();
		user.setFavoriteMovies(Collections.singletonList(movie));
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addFavorite(userId, movieId));
		Assertions.assertEquals(1, user.getFavoriteMovies().size());
		Mockito.verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void removeFavorite_Success() {
		String userId = "1";
		int movieId = 123;
		User user = new User(userId, "test@example.com", BCrypt.hashpw("Passw0rd!", BCrypt.gensalt()), new ArrayList<>());
		MovieExtended movie = new MovieExtended();
		movie.setId(123L);
		movie.setTitle("Test");
		movie.setOverview("Test");
		movie.setReleaseYear("2021");
		List<MovieExtended> movies = new ArrayList<>();
		movies.add(movie);
		user.setFavoriteMovies(movies);
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(userFactory.createUserDTO(user)).thenReturn(new UserDTO(userId, "test@example.com", Collections.emptyList()));
		Mockito.when(session.getAttribute(SessionConstants.USER_ID)).thenReturn(userId);
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
		UserDTO result = userService.removeFavorite(userId, movieId);

		Assertions.assertEquals(0, result.getFavoriteMovies().size());
		Mockito.verify(userRepository).save(user);
	}

	@Test
	void removeFavorite_MovieNotInFavorites() {
		String userId = "1";
		int movieId = 123;
		User user = new User(userId, "test@example.com", BCrypt.hashpw("Passw0rd!", BCrypt.gensalt()), new ArrayList<>());
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		Assertions.assertThrows(IllegalArgumentException.class, () -> userService.removeFavorite(userId, movieId));
		Assertions.assertEquals(0, user.getFavoriteMovies().size());
		Mockito.verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void logoutUser_Success() {
		String userId = "1";
		User user = new User(userId, "test@example.com", BCrypt.hashpw("Passw0rd!", BCrypt.gensalt()), Collections.emptyList());
		Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		Mockito.when(session.getAttribute(SessionConstants.USER_ID)).thenReturn(userId);
		Mockito.when(userFactory.createUserDTO(user)).thenReturn(new UserDTO(userId, "test@example.com", Collections.emptyList()));

		UserDTO result = userService.logoutUser(userId);

		Assertions.assertEquals(user.getId(), result.getId());
		Assertions.assertEquals(user.getEmail(), result.getEmail());
		Mockito.verify(session).removeAttribute(SessionConstants.USER_ID);
	}
}
