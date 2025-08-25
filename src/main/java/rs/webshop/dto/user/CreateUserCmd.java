package rs.webshop.dto.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.webshop.dto.paypal_account.CreatePayPalAccountCmd;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCmd implements Serializable {

  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private CreatePayPalAccountCmd payPalAccount;

}
