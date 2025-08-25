package rs.webshop.service;

import java.util.List;
import rs.webshop.domain.User;
import rs.webshop.dto.user.CreateUserCmd;
import rs.webshop.dto.user.UpdateUserCmd;
import rs.webshop.dto.user.UpdateUserRoleCmd;
import rs.webshop.dto.user.UserInfo;
import rs.webshop.dto.user.UserResult;
import rs.webshop.exception.DAOException;
import rs.webshop.exception.ServiceException;

public interface UserService {

  User save(CreateUserCmd cmd) throws ServiceException;

  List<UserResult> findAll();

  UserInfo findById(Long id);

  void update(UpdateUserCmd cmd) throws ServiceException;

  void delete(Long id) throws ServiceException;

  User addNewRole(UpdateUserRoleCmd cmd) throws ServiceException, DAOException;

}
