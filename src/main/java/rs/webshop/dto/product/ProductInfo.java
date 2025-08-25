package rs.webshop.dto.product;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

  private Long id;
  private Long productId;
  private String name;
  private BigDecimal price;
  private Integer quantity;
}
