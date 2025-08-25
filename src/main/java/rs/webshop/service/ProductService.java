package rs.webshop.service;

import java.util.List;
import rs.webshop.domain.Product;
import rs.webshop.dto.product.CreateProductCmd;
import rs.webshop.dto.product.ProductInfo;
import rs.webshop.dto.product.ProductResult;
import rs.webshop.dto.product.UpdateProductCmd;
import rs.webshop.exception.ServiceException;

public interface ProductService {

  Product save(CreateProductCmd cmd) throws ServiceException;

  List<ProductResult> findAll();

  ProductInfo findById(Long id);

  void update(UpdateProductCmd ProductDTO) throws ServiceException;

  void delete(Long id) throws ServiceException;

}
