package rs.webshop.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import rs.webshop.domain.OrderEvent;

@Repository
public interface OrderEventDao extends AbstractDAO<OrderEvent, Long> {

  List<OrderEvent> findByCompletedDateIsNull();

  Optional<OrderEvent> findById(Long id);


}