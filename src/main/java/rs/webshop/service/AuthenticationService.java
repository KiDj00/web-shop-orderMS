package rs.webshop.service;

import rs.webshop.domain.User;
import rs.webshop.dto.user.LoginUserDto;
import rs.webshop.dto.user.RegisterUserDto;
import rs.webshop.exception.DAOException;

public interface AuthenticationService {

  User signup(RegisterUserDto input) throws DAOException;

  User authenticate(LoginUserDto input);

  void logout(String token);

  User findByUsername(String username);

}
