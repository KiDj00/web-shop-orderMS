package rs.webshop.dao.impl;

import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import rs.webshop.dao.OrderEventDao;
import rs.webshop.domain.OrderEvent;

@Repository
@Primary
public class OrderEventDaoImpl extends AbstractDAOImpl<OrderEvent, Long> implements OrderEventDao {

  @Override
  public List<OrderEvent> findByCompletedDateIsNull() {
    TypedQuery<OrderEvent> query = entityManager.createQuery(
        "SELECT e FROM OrderEvent e WHERE e.completedDate IS NULL", OrderEvent.class
    );
    return query.getResultList();
  }

  @Override
  public Optional<OrderEvent> findById(Long id) {
    return Optional.ofNullable(entityManager.find(OrderEvent.class, id));
  }
}
