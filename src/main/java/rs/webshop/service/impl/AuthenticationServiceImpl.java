package rs.webshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.webshop.config.DaoAuthenticationProvider;
import rs.webshop.dao.RoleDAO;
import rs.webshop.dao.UserDAO;
import rs.webshop.domain.Role;
import rs.webshop.domain.RoleEnum;
import rs.webshop.domain.User;
import rs.webshop.dto.user.LoginUserDto;
import rs.webshop.dto.user.RegisterUserDto;
import rs.webshop.exception.DAOException;
import rs.webshop.filter.TokenBlacklist;
import rs.webshop.service.AuthenticationService;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserDAO userDAO;
  private final RoleDAO roleDAO;
  private final PasswordEncoder passwordEncoder;
  private final DaoAuthenticationProvider daoAuthenticationProvider;
  private final TokenBlacklist tokenBlacklist;

  public AuthenticationServiceImpl(RoleDAO roleDAO, UserDAO userDAO,
      PasswordEncoder passwordEncoder, DaoAuthenticationProvider daoAuthenticationProvider,TokenBlacklist tokenBlacklist) {
    this.userDAO = userDAO;
    this.passwordEncoder = passwordEncoder;
    this.daoAuthenticationProvider = daoAuthenticationProvider;
    this.roleDAO = roleDAO;
    this.tokenBlacklist = tokenBlacklist;
  }

  @Override
  public User signup(RegisterUserDto input) throws DAOException {
    User user = new User();
    user.setUsername(input.getUsername());
    user.setPassword(passwordEncoder.encode(input.getPassword()));
    user.setFirstName(input.getFirstName());
    user.setLastName(input.getLastName());
    List<Role> roles = new ArrayList<>();
    Role role = roleDAO.findByName(RoleEnum.USER.name());
    if (role != null) {
      roles.add(role);
      user.setRoles(roles);
    }
    return userDAO.save(user);
  }


  @Override
  public User authenticate(LoginUserDto input) {
    Authentication authentication = daoAuthenticationProvider.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getUsername(),
            input.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return (User) authentication.getPrincipal();

//        Object principal = authentication.getPrincipal();
//        if (principal instanceof User) {
//            return (User) principal;
//        } else {
//            throw new ClassCastException("Principal is not of type User.");
//        }
  }

  @Override
  public void logout(String token) {
    tokenBlacklist.addToken(token);
  }

  @Override
  public User findByUsername(String username) {
    return userDAO.findByUsername(username).orElse(null);
  }
}
