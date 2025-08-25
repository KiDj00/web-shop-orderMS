package rs.webshop.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.webshop.dao.OrderEventDao;
import rs.webshop.domain.OrderEvent;
import rs.webshop.exception.DAOException;

@RestController
@RequestMapping("/events")
public class OrderEventController {

  private final OrderEventDao orderEventDao;

  public OrderEventController(OrderEventDao orderEventDao) {
    this.orderEventDao = orderEventDao;
  }

  @GetMapping("/pending")
  public List<OrderEvent> getPendingEvents() {
    return orderEventDao.findByCompletedDateIsNull();
  }

  @PostMapping("/{id}/complete")
  public void completeEvent(@PathVariable Long id) throws DAOException {
    OrderEvent event = orderEventDao.findById(id)
        .orElseThrow(() -> new RuntimeException("Event not found"));
    event.setCompletedDate(LocalDateTime.now());
    orderEventDao.save(event);
  }
}