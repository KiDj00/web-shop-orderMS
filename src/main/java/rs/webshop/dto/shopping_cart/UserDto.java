package rs.webshop.dto.shopping_cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private String username;
  private String password;
  private String firstName;
  private String lastName;
}
