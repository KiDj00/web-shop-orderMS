package rs.webshop.dao;

import java.util.Optional;
import rs.webshop.domain.User;

public interface UserDAO extends AbstractDAO<User, Long> {

  Optional<User> findByUsername(String username);
}
