package rs.webshop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Role extends BaseEntity<Long> {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  @NotNull
  private RoleEnum name;

  @Column
  private String description;
}
