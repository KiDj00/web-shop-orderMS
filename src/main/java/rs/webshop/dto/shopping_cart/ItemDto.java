package rs.webshop.dto.shopping_cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

  private Long id;
  private Integer quantity;
  private ProductDto productDto;
}
