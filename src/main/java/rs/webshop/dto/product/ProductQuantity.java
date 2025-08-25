package rs.webshop.dto.product;

public class ProductQuantity {

  public Long productId;
  public Integer quantity;

  public ProductQuantity(Long productId, Integer quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }
}