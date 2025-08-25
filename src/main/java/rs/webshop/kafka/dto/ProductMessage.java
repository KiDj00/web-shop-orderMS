package rs.webshop.kafka.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductMessage {

  private Long productId;
  private String name;
  private String description;
  private String action;
  private Integer quantity;
  private BigDecimal price;
}