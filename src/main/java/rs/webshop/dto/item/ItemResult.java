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
public class ItemResult implements Serializable {

  private Long id;
  private Integer quantity;
  private ProductDto productDto;

}
