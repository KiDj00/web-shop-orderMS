package rs.webshop.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfo {

  private Long id;
  private Integer quantity;
  private ProductDto productDto;
}
