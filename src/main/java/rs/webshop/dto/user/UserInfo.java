package rs.webshop.dto.user;

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
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private PayPalAccountInfo payPalAccountInfo;
}
