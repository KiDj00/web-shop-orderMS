package rs.webshop.service;

import java.util.List;
import rs.webshop.domain.Role;
import rs.webshop.dto.role.CreateRoleCmd;
import rs.webshop.dto.role.RoleInfo;
import rs.webshop.dto.role.RoleResult;
import rs.webshop.dto.role.UpdateRoleCmd;
import rs.webshop.exception.ServiceException;

public interface RoleService {

  Role save(CreateRoleCmd cmd) throws ServiceException;

  List<RoleResult> findAll();

  RoleInfo findById(Long id);

  void update(UpdateRoleCmd updateRoleCmd) throws ServiceException;

  void delete(Long id) throws ServiceException;
}
