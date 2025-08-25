package rs.webshop.dto.paypal_account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.webshop.domain.Address;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePayPalAccountCmd implements Serializable {

  private String accountNumber;
  private BigDecimal budget;
  private String language;
  private LocalDate expiresOn;
  private Address billingAddress;
  private Long userID;

}
