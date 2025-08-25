package rs.webshop.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rs.webshop.dto.product.ProductInfo;

@FeignClient(name = "product-service", url = "http://localhost:8082")
public interface ProductClient {

  @GetMapping("/product/{id}")
  ProductInfo getProductById(@PathVariable("id") Long id);
}
