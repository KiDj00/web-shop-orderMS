package rs.webshop.service;

import java.util.List;
import rs.webshop.domain.ShoppingCart;
import rs.webshop.dto.item.CreateItemCmd;
import rs.webshop.dto.shopping_cart.CreateShoppingCartCmd;
import rs.webshop.dto.shopping_cart.ShoppingCartInfo;
import rs.webshop.dto.shopping_cart.ShoppingCartResult;
import rs.webshop.dto.shopping_cart.UpdateShoppingCartCmd;
import rs.webshop.exception.BudgetExceededException;
import rs.webshop.exception.DAOException;
import rs.webshop.exception.ServiceException;

public interface ShoppingCartService {

  ShoppingCart save(CreateShoppingCartCmd cmd) throws ServiceException;

  List<ShoppingCartResult> findAll();

  ShoppingCartInfo findById(Long id);

  void update(UpdateShoppingCartCmd updateShoppingCartCmd) throws ServiceException;

  void delete(Long id) throws ServiceException;

  ShoppingCart addItem(CreateItemCmd cmd) throws ServiceException, DAOException;

  ShoppingCart removeItem(Long itemId) throws ServiceException, DAOException;

  ShoppingCart checkout(Long id) throws ServiceException, DAOException, BudgetExceededException;

  ShoppingCart closeShoppingCart(ShoppingCart cart) throws ServiceException, DAOException;
}
