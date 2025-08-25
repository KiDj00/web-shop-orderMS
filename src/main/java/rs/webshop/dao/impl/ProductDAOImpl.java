package rs.webshop.dao.impl;

import java.util.Optional;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import rs.webshop.dao.ProductDAO;
import rs.webshop.domain.Product;

@Repository
public class ProductDAOImpl extends AbstractDAOImpl<Product, Long> implements ProductDAO {

  @Override
  public Optional<Product> findByProductId(Long productId) {
    try {
      Product product = entityManager.createQuery(
              "SELECT p FROM Product p WHERE p.productId = :productId", Product.class)
          .setParameter("productId", productId)
          .getSingleResult();
      return Optional.of(product);
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }
}
