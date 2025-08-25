package rs.webshop.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class User extends BaseEntity<Long> {

  @Column(nullable = false, unique = true)
  @NotNull
  private String username;

  @Column
  @NotNull
  private String password;

  @Column(name = "first_name")
  @NotNull
  private String firstName;

  @Column(name = "last_name")
  @NotNull
  private String lastName;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<ShoppingCart> shoppingCarts = new ArrayList<>();

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private PayPalAccount payPalAccount;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private List<Role> roles = new ArrayList<>();

}
