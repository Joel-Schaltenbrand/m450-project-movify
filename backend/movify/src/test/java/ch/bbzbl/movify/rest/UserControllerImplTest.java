package ch.bbzbl.movify.rest;

import ch.bbzbl.movify.model.user.UserDTO;
import ch.bbzbl.movify.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {
	@InjectMocks
	private UserControllerImpl userController;

	@Mock
	private UserService userService;

	@Test
	void testCreateUser_Success() {
		UserDTO userDTO = new UserDTO();
		Mockito.when(userService.createUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.createUser("test@example.com", "password");

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals(userDTO, response.getBody());
	}

	@Test
	void testCreateUser_Conflict() {
		Mockito.doThrow(new IllegalArgumentException("User already exists")).when(userService).createUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

		ResponseEntity<UserDTO> response = userController.createUser("existingUser@example.com", "password");

		Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	void testLoginUser_Success() {
		UserDTO userDTO = new UserDTO();
		Mockito.when(userService.loginUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.loginUser("test@example.com", "password");

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(userDTO, response.getBody());
	}

	@Test
	void testLoginUser_Unauthorized() {
		Mockito.doThrow(new IllegalArgumentException("Wrong password")).when(userService).loginUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());

		ResponseEntity<UserDTO> response = userController.loginUser("test@example.com", "wrongPassword");

		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	void testLogoutUser_Success() {
		UserDTO userDTO = new UserDTO();
		Mockito.when(userService.logoutUser(ArgumentMatchers.anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.logoutUser("testUserId");

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(userDTO, response.getBody());
	}

	@Test
	void testLogoutUser_NotFound() {
		Mockito.doThrow(new IllegalArgumentException("User not found")).when(userService).logoutUser(ArgumentMatchers.anyString());

		ResponseEntity<UserDTO> response = userController.logoutUser("nonexistentUserId");

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testGetFavoriteMovies_Success() {
		UserDTO userDTO = new UserDTO();
		Mockito.when(userService.getFavoriteMovies(ArgumentMatchers.anyString())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.getFavoriteMovies("testUserId");

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(userDTO, response.getBody());
	}

	@Test
	void testGetFavoriteMovies_NotFound() {
		Mockito.doThrow(new IllegalArgumentException("User not found")).when(userService).getFavoriteMovies(ArgumentMatchers.anyString());

		ResponseEntity<UserDTO> response = userController.getFavoriteMovies("nonexistentUserId");

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testAddFavorite_Success() {
		UserDTO userDTO = new UserDTO();
		Mockito.when(userService.addFavorite(ArgumentMatchers.anyString(), Mockito.anyInt())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.addFavorite("testUserId", 123);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(userDTO, response.getBody());
	}

	@Test
	void testAddFavorite_Conflict() {
		Mockito.doThrow(new IllegalArgumentException("Movie already in favorites")).when(userService).addFavorite(ArgumentMatchers.anyString(), Mockito.anyInt());

		ResponseEntity<UserDTO> response = userController.addFavorite("testUserId", 123);

		Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	void testRemoveFavorite_Success() {
		UserDTO userDTO = new UserDTO();
		Mockito.when(userService.removeFavorite(ArgumentMatchers.anyString(), Mockito.anyInt())).thenReturn(userDTO);

		ResponseEntity<UserDTO> response = userController.removeFavorite("testUserId", 123);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(userDTO, response.getBody());
	}

	@Test
	void testRemoveFavorite_NotFound() {
		Mockito.doThrow(new IllegalArgumentException("Movie not in favorites")).when(userService).removeFavorite(ArgumentMatchers.anyString(), Mockito.anyInt());

		ResponseEntity<UserDTO> response = userController.removeFavorite("testUserId", 123);

		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}
