package rs.webshop.dao;

import rs.webshop.domain.Item;

public interface ItemDAO extends AbstractDAO<Item, Long> {

  Item findByProductId(Long id);
}
