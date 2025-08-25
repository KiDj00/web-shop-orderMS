package rs.webshop.dao.impl;

import org.springframework.stereotype.Repository;
import rs.webshop.dao.ShoppingCartDAO;
import rs.webshop.domain.ShoppingCart;

@Repository
public class ShoppingCartDAOImpl extends AbstractDAOImpl<ShoppingCart, Long> implements
    ShoppingCartDAO {

}
