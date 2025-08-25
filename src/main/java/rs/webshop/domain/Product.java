package rs.webshop.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity<Long> {

  @Column(nullable = false, unique = true)
  @NotNull
  private String name;


  @Column(nullable = false, unique = true)
  @NotNull
  private Long productId;

  @Column
  @NotNull
  private BigDecimal price;

  @Column(nullable = false)
  @NotNull
  private Integer quantity;

}
