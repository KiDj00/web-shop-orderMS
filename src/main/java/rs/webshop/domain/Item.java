package rs.webshop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Item extends BaseEntity<Long> {

  @Column(nullable = false)
  @NotNull
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "shopping_cart_id")
  private ShoppingCart shoppingCart;

}
