package rs.webshop.dao;

import java.util.Optional;
import rs.webshop.domain.Product;

public interface ProductDAO extends AbstractDAO<Product, Long> {

  Optional<Product> findByProductId(Long productId);

}
