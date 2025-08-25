package rs.webshop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart extends BaseEntity<Long> {

  @Column
  @NotNull
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @NotNull
  private Status status;

  @Column
  private BigDecimal price;

  @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Item> items = new ArrayList<>();

  @ManyToOne
  @JsonBackReference
  private User user;
}
