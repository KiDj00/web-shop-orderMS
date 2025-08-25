package rs.webshop.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rs.webshop.domain.User;
import rs.webshop.dto.user.CreateUserCmd;
import rs.webshop.dto.user.UpdateUserCmd;
import rs.webshop.dto.user.UpdateUserRoleCmd;
import rs.webshop.dto.user.UserInfo;
import rs.webshop.dto.user.UserResult;
import rs.webshop.exception.DAOException;
import rs.webshop.exception.ServiceException;
import rs.webshop.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  public User save(@RequestBody @Valid CreateUserCmd cmd) throws ServiceException {
    return userService.save(cmd);
  }

  @GetMapping()
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @ResponseBody
  public List<UserResult> findAll() {
    return userService.findAll();
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public UserInfo findById(@PathVariable Long id) {
    return userService.findById(id);
  }

  @PutMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@RequestBody @Valid UpdateUserCmd cmd) throws ServiceException {
    userService.update(cmd);
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) throws ServiceException {
    userService.delete(id);
  }

  @PostMapping(value = "/add-new-role")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public void addNewRole(@RequestBody @Valid UpdateUserRoleCmd cmd)
      throws ServiceException, DAOException {
    userService.addNewRole(cmd);
  }
}
