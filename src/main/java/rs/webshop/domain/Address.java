package rs.webshop.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  private String city;
  private String country;
  @Column(name = "postal_code")
  private String postalCode;
  private String street;
}
