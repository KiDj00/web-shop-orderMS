package rs.webshop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PayPalAccount extends BaseEntity<Long> {

  @Column(nullable = false, name = "account_number", unique = true)
  @NotNull
  private String accountNumber;

  @Column(nullable = false)
  @NotNull
  private BigDecimal budget;

  @Column(nullable = false)
  @NotNull
  private String language;

  @Column(nullable = false, name = "expires_on")
  @NotNull
  private LocalDate expiresOn;

  @Embedded
  private Address billingAddress;

  @OneToOne
  @JoinColumn(name = "user_id")
  @NotNull
  @JsonBackReference
  private User user;
}
