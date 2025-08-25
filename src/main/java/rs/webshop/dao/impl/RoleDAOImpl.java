package rs.webshop.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import rs.webshop.dao.RoleDAO;
import rs.webshop.domain.Role;
import rs.webshop.domain.RoleEnum;

@Repository
public class RoleDAOImpl extends AbstractDAOImpl<Role, Long> implements RoleDAO {

  @Override
  public Role findByName(String name) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Role> criteria = cb.createQuery(Role.class);
    Root<Role> role = criteria.from(Role.class);
    RoleEnum roleEnum = RoleEnum.valueOf(name);
    criteria.select(role)
        .where(cb.equal(role.get("name"), roleEnum));

    return entityManager.createQuery(criteria).getSingleResult();
  }
}
