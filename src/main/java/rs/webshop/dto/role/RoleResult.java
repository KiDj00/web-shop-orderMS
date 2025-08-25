package rs.webshop.dto.role;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.webshop.domain.RoleEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResult implements Serializable {

  private Long id;
  private RoleEnum name;
  private String description;
}
