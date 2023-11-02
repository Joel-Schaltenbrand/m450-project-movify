package ch.bbzbl.movify.service;

import ch.bbzbl.movify.model.user.UserDTO;

public interface UserService {
	UserDTO createUser(String email, String password);

	UserDTO loginUser(String email, String password);

	UserDTO getFavoriteMovies(String id);

	UserDTO addFavorite(String id, int movieId);

	UserDTO removeFavorite(String id, int movieId);

	UserDTO logoutUser(String id);
}
