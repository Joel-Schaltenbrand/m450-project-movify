package ch.bbzbl.movify.rest;

import ch.bbzbl.movify.model.user.UserDTO;
import ch.bbzbl.movify.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {
	@InjectMocks
	private UserControllerImpl userController;

	@Mock
	private UserService userService;

	@Test
	void testCreateUser_Success() {
		UserDTO userDTO = new UserDTO();
		when(userService.createUser(anyString(), anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.createUser("test@example.com", "password");

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(userDTO, response.getBody());
	}

	@Test
	void testCreateUser_Conflict() {
		doThrow(new IllegalArgumentException("User already exists")).when(userService).createUser(anyString(), anyString());

		ResponseEntity<UserDTO> response = userController.createUser("existingUser@example.com", "password");

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	void testLoginUser_Success() {
		UserDTO userDTO = new UserDTO();
		when(userService.loginUser(anyString(), anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.loginUser("test@example.com", "password");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userDTO, response.getBody());
	}

	@Test
	void testLoginUser_Unauthorized() {
		doThrow(new IllegalArgumentException("Wrong password")).when(userService).loginUser(anyString(), anyString());

		ResponseEntity<UserDTO> response = userController.loginUser("test@example.com", "wrongPassword");

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	void testLogoutUser_Success() {
		UserDTO userDTO = new UserDTO();
		when(userService.logoutUser(anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.logoutUser("testUserId");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userDTO, response.getBody());
	}

	@Test
	void testLogoutUser_NotFound() {
		doThrow(new IllegalArgumentException("User not found")).when(userService).logoutUser(anyString());

		ResponseEntity<UserDTO> response = userController.logoutUser("nonexistentUserId");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testGetFavoriteMovies_Success() {
		UserDTO userDTO = new UserDTO();
		when(userService.getFavoriteMovies(anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.getFavoriteMovies("testUserId");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userDTO, response.getBody());
	}

	@Test
	void testGetFavoriteMovies_NotFound() {
		doThrow(new IllegalArgumentException("User not found")).when(userService).getFavoriteMovies(anyString());

		ResponseEntity<UserDTO> response = userController.getFavoriteMovies("nonexistentUserId");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testAddFavorite_Success() {
		UserDTO userDTO = new UserDTO();
		when(userService.addFavorite(anyString(), Mockito.anyInt())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.addFavorite("testUserId", 123);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userDTO, response.getBody());
	}

	@Test
	void testAddFavorite_Conflict() {
		doThrow(new IllegalArgumentException("Movie already in favorites")).when(userService).addFavorite(anyString(), Mockito.anyInt());

		ResponseEntity<UserDTO> response = userController.addFavorite("testUserId", 123);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	void testRemoveFavorite_Success() {
		UserDTO userDTO = new UserDTO();
		when(userService.removeFavorite(anyString(), Mockito.anyInt())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.removeFavorite("testUserId", 123);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userDTO, response.getBody());
	}

	@Test
	void testRemoveFavorite_NotFound() {
		doThrow(new IllegalArgumentException("Movie not in favorites")).when(userService).removeFavorite(anyString(), Mockito.anyInt());

		ResponseEntity<UserDTO> response = userController.removeFavorite("testUserId", 123);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}
