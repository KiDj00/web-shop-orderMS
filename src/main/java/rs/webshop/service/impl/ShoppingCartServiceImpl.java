package rs.webshop.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.webshop.client.ProductClient;
import rs.webshop.dao.ItemDAO;
import rs.webshop.dao.OrderEventDao;
import rs.webshop.dao.PayPalAccountDAO;
import rs.webshop.dao.ProductDAO;
import rs.webshop.dao.ShoppingCartDAO;
import rs.webshop.dao.UserDAO;
import rs.webshop.domain.Item;
import rs.webshop.domain.OrderEvent;
import rs.webshop.domain.PayPalAccount;
import rs.webshop.domain.ShoppingCart;
import rs.webshop.domain.Status;
import rs.webshop.domain.User;
import rs.webshop.dto.item.CreateItemCmd;
import rs.webshop.dto.product.ProductInfo;
import rs.webshop.dto.product.ProductQuantity;
import rs.webshop.dto.shopping_cart.CreateShoppingCartCmd;
import rs.webshop.dto.shopping_cart.ShoppingCartInfo;
import rs.webshop.dto.shopping_cart.ShoppingCartResult;
import rs.webshop.dto.shopping_cart.UpdateShoppingCartCmd;
import rs.webshop.exception.BudgetExceededException;
import rs.webshop.exception.DAOException;
import rs.webshop.exception.ErrorCode;
import rs.webshop.exception.ServiceException;
import rs.webshop.mapper.ItemMapper;
import rs.webshop.mapper.ShoppingCartMapper;
import rs.webshop.service.ShoppingCartService;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {


  private final static Logger LOGGER = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

  private final ProductClient productClient;
  private final ShoppingCartDAO shoppingCartDAO;
  private final UserDAO userDAO;
  private final ItemDAO itemDAO;
  private final PayPalAccountDAO payPalAccountDAO;
  private final OrderEventDao orderDAO;
  private final ProductDAO productDAO;

  public ShoppingCartServiceImpl(ShoppingCartDAO shoppingCartDAO, UserDAO userDAO, ItemDAO itemDAO,
      PayPalAccountDAO payPalAccountDAO, OrderEventDao orderDAO, ProductDAO productDAO,
      ProductClient productClient) {
    this.shoppingCartDAO = shoppingCartDAO;
    this.userDAO = userDAO;
    this.itemDAO = itemDAO;
    this.payPalAccountDAO = payPalAccountDAO;
    this.orderDAO = orderDAO;
    this.productDAO = productDAO;
    this.productClient = productClient;
  }

  @Override
  public ShoppingCart save(CreateShoppingCartCmd cmd) throws ServiceException {
    User user = userDAO.findOne(cmd.getUserId());

    ShoppingCart shoppingCart = ShoppingCartMapper.INSTANCE.createShoppingCartCmdToShoppingCart(
        cmd);
    shoppingCart.setUser(user);
    shoppingCart.setStatus(Status.NEW);

    try {
      shoppingCart = shoppingCartDAO.save(shoppingCart);
    } catch (DAOException e) {
      LOGGER.error(null, e);
      throw new ServiceException(ErrorCode.ERR_GEN_001, "Saving of shopping cart failed!", e);
    }
    return shoppingCart;
  }

  @Override
  public List<ShoppingCartResult> findAll() {
    return ShoppingCartMapper.INSTANCE.listShoppingCartToListShoppingCartResult(
        shoppingCartDAO.findAll());
  }

  @Override
  public ShoppingCartInfo findById(Long id) {
    return ShoppingCartMapper.INSTANCE.shoppingCartToShoppingCartInfo(shoppingCartDAO.findOne(id));
  }

  @Override
  public void update(UpdateShoppingCartCmd cmd) throws ServiceException {
    ShoppingCart shoppingCart;
    try {
      shoppingCart = shoppingCartDAO.findOne(cmd.getId());
      if (shoppingCart == null) {
        throw new ServiceException(ErrorCode.ERR_GEN_002);
      }

      ShoppingCartMapper.INSTANCE.updateShoppingCartCmdToShoppingCart(shoppingCart, cmd);
      shoppingCartDAO.update(shoppingCart);
    } catch (DAOException e) {
      LOGGER.error(null, e);
      throw new ServiceException(ErrorCode.ERR_GEN_001, "Update of shopping cart failed!", e);
    }
  }

  @Override
  public void delete(Long id) throws ServiceException {
    ShoppingCart shoppingCart = shoppingCartDAO.findOne(id);
    if (shoppingCart != null) {
      try {
        shoppingCartDAO.delete(shoppingCart);
      } catch (DAOException e) {
        LOGGER.error(null, e);
        throw new ServiceException(ErrorCode.ERR_GEN_001, "Delete of shopping cart failed!", e);
      }
    } else {
      throw new ServiceException(ErrorCode.ERR_CAT_001, "Shopping cart does not exist!");
    }
  }

  @Override
  public ShoppingCart addItem(CreateItemCmd cmd) throws ServiceException, DAOException {
    ShoppingCart cart = shoppingCartDAO.findOne(cmd.getShoppingCartID());
    cart.setStatus(Status.ACTIVE);
    Item item = ItemMapper.INSTANCE.createItemCmdToItem(cmd);
    item.setProduct(productDAO.findOne(cmd.getProductID()));
    item.setShoppingCart(cart);

    cart.getItems().add(item);
    cart.setStatus(Status.ACTIVE);

    if (cart.getPrice() != null) {
      cart.setPrice(cart.getPrice().add(item.getProduct().getPrice()));
    } else {
      cart.setPrice(item.getProduct().getPrice());
    }
    return shoppingCartDAO.update(cart);
  }

  @Override
  public ShoppingCart removeItem(Long itemId) throws ServiceException, DAOException {
    Item item = itemDAO.findOne(itemId);
    ShoppingCart cart = shoppingCartDAO.findOne(item.getShoppingCart().getId());

    if (!cart.getItems().contains(item)) {
      return null;
    } else {
      cart.getItems().remove(item);
      ShoppingCart c = shoppingCartDAO.update(cart);
      Item i = itemDAO.findOne(item.getId());
      itemDAO.delete(i);
      return c;
    }
  }

  @Override
  public ShoppingCart checkout(Long id)
      throws ServiceException, DAOException, BudgetExceededException {
    ShoppingCart cart = shoppingCartDAO.findOne(id);

    if (!cart.getStatus().equals(Status.ACTIVE)) {
      throw new ServiceException(ErrorCode.ERR_SC_001, "Shopping cart is not active!");
    }

    List<Item> items = cart.getItems();

    BigDecimal total = BigDecimal.valueOf(0);
    // Provera kolicine i budzeta
    for (Item i : items) {
      ProductInfo product = productClient.getProductById(i.getProduct().getProductId());
      if (product.getQuantity() < i.getQuantity()) {
        throw new ServiceException(ErrorCode.ERR_P_001, "Not enough products in stock!");
      }
      total = total.add(product.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
    }
    PayPalAccount paypal = cart.getUser().getPayPalAccount();
    if (paypal.getBudget().compareTo(total) < 0) {
      throw new BudgetExceededException(ErrorCode.ERR_PP_001, "Not enough budget!");
    }

    // Kreiranje JSON poruke sa svim proizvodima
    ObjectMapper mapper = new ObjectMapper();
    List<ProductQuantity> productList = new ArrayList<>();
    for (Item i : items) {
      productList.add(new ProductQuantity(i.getProduct().getProductId(), i.getQuantity()));
    }
    String productsJson;
    try {
      productsJson = mapper.writeValueAsString(productList);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    // Snimanje eventa u bazu
    OrderEvent event = new OrderEvent("OrderCreated", productsJson);
    orderDAO.save(event);

    // Smanjenje budzeta korisnika
    paypal.setBudget(paypal.getBudget().subtract(sum(items)));
    payPalAccountDAO.update(paypal);

    cart.setStatus(Status.COMPLETED);
    return shoppingCartDAO.update(cart);
  }

  @Override
  public ShoppingCart closeShoppingCart(ShoppingCart cart) throws ServiceException, DAOException {
    cart.setStatus(Status.INACTIVE);
    return shoppingCartDAO.update(cart);
  }

  public BigDecimal sum(List<Item> items) {
    BigDecimal sum = BigDecimal.ZERO;
    for (Item i : items) {
      sum = sum.add(i.getProduct().getPrice());
    }
    return sum;
  }
}
