package rs.webshop.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rs.webshop.dto.product.CreateProductCmd;
import rs.webshop.dto.product.UpdateProductCmd;
import rs.webshop.kafka.dto.ProductMessage;
import rs.webshop.service.ProductService;

@Component
public class ProductEventConsumer {

  private final ProductService productService;

  public ProductEventConsumer(ProductService productService) {
    this.productService = productService;
  }

  @KafkaListener(topics = "product-events", groupId = "order-group")
  public void consume(ProductMessage event) {
    System.out.println("📥 Received product event: " + event);

    try {
      switch (event.getAction()) {
        case "CREATE":
          CreateProductCmd createCmd = new CreateProductCmd();
          createCmd.setProductId(event.getProductId());
          createCmd.setName(event.getName());
          createCmd.setPrice(event.getPrice());
          createCmd.setQuantity(event.getQuantity());
          productService.save(createCmd);
          break;

        case "UPDATE":
          UpdateProductCmd updateCmd = new UpdateProductCmd();
          updateCmd.setProductId(event.getProductId());
          updateCmd.setName(event.getName());
          updateCmd.setPrice(event.getPrice());
          updateCmd.setQuantity(event.getQuantity());
          productService.update(updateCmd);
          break;

        case "DELETE":
          productService.delete(event.getProductId());
          break;
      }
    } catch (Exception e) {
      System.err.println("Error while processing product event: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
