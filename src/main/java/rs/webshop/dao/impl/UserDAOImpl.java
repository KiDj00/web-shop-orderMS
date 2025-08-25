package rs.webshop.dao.impl;

import java.util.Optional;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import rs.webshop.dao.UserDAO;
import rs.webshop.domain.User;

@Repository
public class UserDAOImpl extends AbstractDAOImpl<User, Long> implements UserDAO {

  @Override
  public Optional<User> findByUsername(String username) {
    String jpql = "SELECT u FROM User u WHERE u.username = :username";
    TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
    query.setParameter("username", username);
    try {
      User user = query.getSingleResult();
      return Optional.of(user);
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }
}
