package rs.webshop.service;

import java.util.List;
import rs.webshop.domain.Item;
import rs.webshop.dto.item.CreateItemCmd;
import rs.webshop.dto.item.ItemInfo;
import rs.webshop.dto.item.ItemResult;
import rs.webshop.dto.item.UpdateItemCmd;
import rs.webshop.exception.ServiceException;

public interface ItemService {

  Item save(CreateItemCmd cmd) throws ServiceException;

  List<ItemResult> findAll();

  ItemInfo findById(Long id);

  void update(UpdateItemCmd updateItemCmd) throws ServiceException;

  void delete(Long id) throws ServiceException;

  Item findByProductId(Long id);
}
