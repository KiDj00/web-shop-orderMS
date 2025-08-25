package rs.webshop.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.webshop.dao.ItemDAO;
import rs.webshop.dao.ProductDAO;
import rs.webshop.dao.ShoppingCartDAO;
import rs.webshop.domain.Item;
import rs.webshop.domain.Product;
import rs.webshop.domain.ShoppingCart;
import rs.webshop.dto.item.CreateItemCmd;
import rs.webshop.dto.item.ItemInfo;
import rs.webshop.dto.item.ItemResult;
import rs.webshop.dto.item.UpdateItemCmd;
import rs.webshop.exception.DAOException;
import rs.webshop.exception.ErrorCode;
import rs.webshop.exception.ServiceException;
import rs.webshop.mapper.ItemMapper;
import rs.webshop.service.ItemService;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

  private final static Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

  private final ItemDAO itemDAO;
  private final ProductDAO productDAO;
  private final ShoppingCartDAO shoppingCartDAO;

  public ItemServiceImpl(ItemDAO itemDAO, ProductDAO productDAO, ShoppingCartDAO shoppingCartDAO) {
    this.itemDAO = itemDAO;
    this.productDAO = productDAO;
    this.shoppingCartDAO = shoppingCartDAO;
  }

  @Override
  public Item save(CreateItemCmd cmd) throws ServiceException {
    Product product = productDAO.findByProductId(cmd.getProductID()).orElse(null);
    ShoppingCart cart = shoppingCartDAO.findOne(cmd.getShoppingCartID());

    Item item = ItemMapper.INSTANCE.createItemCmdToItem(cmd);
    item.setProduct(product);
    item.setShoppingCart(cart);

    try {
      item = itemDAO.save(item);
    } catch (DAOException e) {
      LOGGER.error(null, e);
      throw new ServiceException(ErrorCode.ERR_GEN_001, "Saving of item failed!", e);
    }
    return item;
  }

  @Override
  public List<ItemResult> findAll() {
    return ItemMapper.INSTANCE.listItemToListItemResult(itemDAO.findAll());
  }

  @Override
  public ItemInfo findById(Long id) {
    return ItemMapper.INSTANCE.itemToItemInfo(itemDAO.findOne(id));
  }

  @Override
  public void update(UpdateItemCmd cmd) throws ServiceException {
    Item item;
    try {
      item = itemDAO.findOne(cmd.getId());
      if (item == null) {
        throw new ServiceException(ErrorCode.ERR_GEN_002);
      }

      ItemMapper.INSTANCE.updateItemCmdToItem(item, cmd);
      itemDAO.update(item);
    } catch (DAOException e) {
      LOGGER.error(null, e);
      throw new ServiceException(ErrorCode.ERR_GEN_001, "Update of item failed!", e);
    }
  }

  @Override
  public void delete(Long id) throws ServiceException {
    Item item = itemDAO.findOne(id);
    if (item != null) {
      try {
        itemDAO.delete(item);
      } catch (DAOException e) {
        LOGGER.error(null, e);
        throw new ServiceException(ErrorCode.ERR_GEN_001, "Delete of item failed!", e);
      }
    } else {
      throw new ServiceException(ErrorCode.ERR_CAT_001, "Item does not exist!");
    }
  }

  @Override
  public Item findByProductId(Long id) {
    return itemDAO.findByProductId(id);
  }
}
