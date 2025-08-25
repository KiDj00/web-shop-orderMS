package rs.webshop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import rs.webshop.domain.Product;
import rs.webshop.dto.product.CreateProductCmd;
import rs.webshop.dto.product.ProductInfo;
import rs.webshop.dto.product.ProductResult;
import rs.webshop.dto.product.UpdateProductCmd;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  Product createProductCmdToProduct(CreateProductCmd cmd);

  List<ProductResult> listProductToListProductResult(List<Product> products);

  ProductInfo productToProductInfo(Product product);

  @Mapping(target = "id", ignore = true)
  void updateProductCmdToProduct(@MappingTarget Product product, UpdateProductCmd cmd);
}
