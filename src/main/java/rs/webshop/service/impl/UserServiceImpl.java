package rs.webshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.webshop.dao.RoleDAO;
import rs.webshop.dao.UserDAO;
import rs.webshop.domain.PayPalAccount;
import rs.webshop.domain.Role;
import rs.webshop.domain.RoleEnum;
import rs.webshop.domain.User;
import rs.webshop.dto.user.*;
import rs.webshop.exception.DAOException;
import rs.webshop.exception.ErrorCode;
import rs.webshop.exception.ServiceException;
import rs.webshop.mapper.UserMapper;
import rs.webshop.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    public User save(CreateUserCmd cmd) throws ServiceException {
        User user = UserMapper.INSTANCE.createUserCmdToUser(cmd);

        if (user.getPayPalAccount() != null) {
            user.getPayPalAccount().setUser(user);
        }
        List<Role> roles = new ArrayList<>();
        Role role = roleDAO.findByName(RoleEnum.USER.name());
        if (role != null) {
            roles.add(role);
            user.setRoles(roles);
        } else
            throw new ServiceException(ErrorCode.ERR_CAT_001, "Role does not exist!");
        try {
            user = userDAO.save(user);
        } catch (DAOException e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Saving of user failed!", e);
        }
        return user;
    }

    @Override
    public List<UserResult> findAll() {
        return UserMapper.INSTANCE.listUserToListUserResult(userDAO.findAll());
    }

    @Override
    public UserInfo findById(Long id) {
        User user = userDAO.findOne(id);
        if (user != null) {
            PayPalAccount p = user.getPayPalAccount();
            return UserMapper.INSTANCE.userToUserInfo(user);
        }
        return null;
    }

    @Override
    public void update(UpdateUserCmd cmd) throws ServiceException {
        User user;
        try {
            user = userDAO.findOne(cmd.getId());

            if (user == null) throw new ServiceException(ErrorCode.ERR_GEN_002);
            UserMapper.INSTANCE.updateUserCmdToUser(user, cmd);

            PayPalAccountInfo payPalAccount = cmd.getPayPalAccount();

            userDAO.update(user);
        } catch (DAOException e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Update of user failed!", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ServiceException {
        User user = userDAO.findOne(id);
        if (user != null) {
            try {
                PayPalAccount payPalAccount = user.getPayPalAccount();
                if (payPalAccount != null) {
                    payPalAccount.setUser(null);
                    user.setPayPalAccount(null);
                }
                userDAO.delete(user);
            } catch (DAOException e) {
                LOGGER.error(null, e);
                throw new ServiceException(ErrorCode.ERR_CAT_001, "User does not exist!");
            }
        }
    }

    @Override
    public User addNewRole(UpdateUserRoleCmd cmd) throws ServiceException, DAOException {
        Role role = roleDAO.findOne(cmd.getRoleId());
        User user = userDAO.findOne(cmd.getUserId());

        if (role == null || user == null) throw new ServiceException(ErrorCode.ERR_GEN_002);

        if (user.getRoles().contains(role))
            throw new ServiceException(ErrorCode.ERR_GEN_005);

        user.getRoles().add(role);

        return userDAO.update(user);
    }

}
