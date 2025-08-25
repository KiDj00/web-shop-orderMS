package rs.webshop.dto.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResult implements Serializable {

  private Long id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
}
