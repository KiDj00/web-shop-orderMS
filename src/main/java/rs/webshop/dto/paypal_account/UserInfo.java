package rs.webshop.dto.paypal_account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

  private Long id;
  private String firstName;
  private String lastName;
}
