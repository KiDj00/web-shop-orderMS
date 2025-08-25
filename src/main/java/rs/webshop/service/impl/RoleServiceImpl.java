package rs.webshop.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.webshop.dao.RoleDAO;
import rs.webshop.domain.Role;
import rs.webshop.dto.role.CreateRoleCmd;
import rs.webshop.dto.role.RoleInfo;
import rs.webshop.dto.role.RoleResult;
import rs.webshop.dto.role.UpdateRoleCmd;
import rs.webshop.exception.DAOException;
import rs.webshop.exception.ErrorCode;
import rs.webshop.exception.ServiceException;
import rs.webshop.mapper.RoleMapper;
import rs.webshop.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

  private final static Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

  private final RoleDAO roleDAO;

  public RoleServiceImpl(RoleDAO roleDAO) {
    this.roleDAO = roleDAO;
  }

  @Override
  public Role save(CreateRoleCmd cmd) throws ServiceException {
    Role role = RoleMapper.INSTANCE.createRoleCmdToRole(cmd);

    try {
      List<Role> roles = roleDAO.findAll();
      for (Role r : roles) {
        if (r.getName() == role.getName()) {
          return r;
        }
      }
      role = roleDAO.save(role);

    } catch (DAOException e) {
      throw new ServiceException(ErrorCode.ERR_GEN_001, "Saving of role failed!", e);
    }
    return role;
  }

  @Override
  public List<RoleResult> findAll() {
    return RoleMapper.INSTANCE.listRoleToListRoleResult(roleDAO.findAll());
  }

  @Override
  public RoleInfo findById(Long id) {
    return RoleMapper.INSTANCE.roleToRoleInfo(roleDAO.findOne(id));
  }

  @Override
  public void update(UpdateRoleCmd updateRoleCmd) throws ServiceException {
    Role role;

    try {
      role = roleDAO.findOne(updateRoleCmd.getId());
      if (role == null) {
        throw new ServiceException(ErrorCode.ERR_GEN_002);
      }

      RoleMapper.INSTANCE.updateRoleCmdToRole(role, updateRoleCmd);
      roleDAO.update(role);
    } catch (DAOException e) {
      LOGGER.error(null, e);
      throw new ServiceException(ErrorCode.ERR_GEN_001, "Update of role failed!", e);
    }
  }

  @Override
  public void delete(Long id) throws ServiceException {
    Role role = roleDAO.findOne(id);
    if (role != null) {
      try {
        roleDAO.delete(role);
      } catch (DAOException e) {
        LOGGER.error(null, e);
        throw new ServiceException(ErrorCode.ERR_CAT_001, "Role does not exist!");
      }
    }
  }

}
