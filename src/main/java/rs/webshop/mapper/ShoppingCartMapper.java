package rs.webshop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import rs.webshop.domain.ShoppingCart;
import rs.webshop.dto.shopping_cart.CreateShoppingCartCmd;
import rs.webshop.dto.shopping_cart.ShoppingCartInfo;
import rs.webshop.dto.shopping_cart.ShoppingCartResult;
import rs.webshop.dto.shopping_cart.UpdateShoppingCartCmd;

@Mapper
public interface ShoppingCartMapper {

  ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

  ShoppingCart createShoppingCartCmdToShoppingCart(CreateShoppingCartCmd cmd);

  List<ShoppingCartResult> listShoppingCartToListShoppingCartResult(
      List<ShoppingCart> shoppingCarts);

  @Mapping(target = "userDto", source = "user")
  ShoppingCartInfo shoppingCartToShoppingCartInfo(ShoppingCart shoppingCart);

  void updateShoppingCartCmdToShoppingCart(@MappingTarget ShoppingCart shoppingCart,
      UpdateShoppingCartCmd cmd);

}
