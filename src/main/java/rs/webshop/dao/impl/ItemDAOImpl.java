package rs.webshop.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import rs.webshop.dao.ItemDAO;
import rs.webshop.domain.Item;

@Repository
public class ItemDAOImpl extends AbstractDAOImpl<Item, Long> implements ItemDAO {

  @Override
  public Item findByProductId(Long id) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Item> criteria = cb.createQuery(Item.class);
    Root<Item> item = criteria.from(Item.class);
    criteria.select(item)
        .where(cb.equal(item.get("product"), id));

    return entityManager.createQuery(criteria).getSingleResult();
  }
}
