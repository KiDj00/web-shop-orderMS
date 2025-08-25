package rs.webshop.dao;

import rs.webshop.domain.Role;

public interface RoleDAO extends AbstractDAO<Role, Long> {

  Role findByName(String name);
}
