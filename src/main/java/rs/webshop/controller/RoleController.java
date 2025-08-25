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
import rs.webshop.domain.Role;
import rs.webshop.dto.role.CreateRoleCmd;
import rs.webshop.dto.role.RoleInfo;
import rs.webshop.dto.role.RoleResult;
import rs.webshop.dto.role.UpdateRoleCmd;
import rs.webshop.exception.ServiceException;
import rs.webshop.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PostMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  public Role save(@RequestBody @Valid CreateRoleCmd cmd) throws ServiceException {
    return roleService.save(cmd);
  }

  @GetMapping()
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @ResponseBody
  public List<RoleResult> findAll() {
    return roleService.findAll();
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public RoleInfo findById(@PathVariable Long id) {
    return roleService.findById(id);
  }

  @PutMapping(value = "/update")
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@RequestBody @Valid UpdateRoleCmd cmd) throws ServiceException {
    roleService.update(cmd);
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) throws ServiceException {
    roleService.delete(id);
  }
}
