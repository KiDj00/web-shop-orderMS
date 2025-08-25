package rs.webshop.dto.item;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemCmd implements Serializable {

  private Integer quantity;
  private Long productID;
  private Long shoppingCartID;
}
