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
import rs.webshop.domain.Item;
import rs.webshop.dto.item.CreateItemCmd;
import rs.webshop.dto.item.ItemInfo;
import rs.webshop.dto.item.ItemResult;
import rs.webshop.dto.item.UpdateItemCmd;
import rs.webshop.exception.ServiceException;
import rs.webshop.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

  private ItemService itemService;

  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @PostMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  public Item save(@RequestBody @Valid CreateItemCmd cmd) throws ServiceException {
    return itemService.save(cmd);
  }

  @GetMapping()
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  @ResponseBody
  public List<ItemResult> findAll() {
    return itemService.findAll();
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
  public ItemInfo findById(@PathVariable Long id) {
    return itemService.findById(id);
  }

  @PutMapping()
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@RequestBody @Valid UpdateItemCmd cmd) throws ServiceException {
    itemService.update(cmd);
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) throws ServiceException {
    itemService.delete(id);
  }
}
