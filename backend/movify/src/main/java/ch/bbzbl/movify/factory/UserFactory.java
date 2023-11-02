package ch.bbzbl.movify.factory;

import ch.bbzbl.movify.model.user.User;
import ch.bbzbl.movify.model.user.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserFactory {
	public UserDTO createUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setFavoriteMovies(user.getFavoriteMovies());
		return userDTO;
	}
}
