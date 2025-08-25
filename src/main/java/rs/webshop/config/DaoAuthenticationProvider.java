package rs.webshop.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import rs.webshop.dao.UserDAO;
import rs.webshop.domain.Role;
import rs.webshop.domain.User;

@Component
public class DaoAuthenticationProvider implements AuthenticationProvider {

  private final UserDAO userDAO;

  @Autowired
  public DaoAuthenticationProvider(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public Authentication authenticate(Authentication authentication) {

    String username = authentication.getName();

    Optional<User> user = userDAO.findByUsername(username);

    if (user != null) {
      User userr = user.get();
      Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
      for (Role role : userr.getRoles()) {
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
      }
      return new UsernamePasswordAuthenticationToken(userr, username, grantedAuthorities);
    } else {
      throw new BadCredentialsException("bad credentials");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(
        UsernamePasswordAuthenticationToken.class);
  }
}
