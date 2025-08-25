package rs.webshop.domain;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SuppressWarnings({"unused", "WeakerAccess"})
@MappedSuperclass
public abstract class BaseEntity<K extends Serializable> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private K id;

}
