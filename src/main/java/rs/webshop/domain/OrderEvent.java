package rs.webshop.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_events")
public class OrderEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String eventName;

  @Column(columnDefinition = "TEXT")
  private String productsJson;

  private LocalDateTime createdAt;

  private LocalDateTime completedDate;

  public OrderEvent(String eventName, String productsJson) {
    this.eventName = eventName;
    this.productsJson = productsJson;
    this.createdAt = LocalDateTime.now();
  }
}