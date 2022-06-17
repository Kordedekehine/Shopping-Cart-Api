package ShoppingCartsAPI.carts.dtos.order;

import javax.validation.constraints.NotNull;

public class OrderItemDto {

    @NotNull
    private double price;
    @NotNull
    private int quantity;
    @NotNull
    private int orderId;
    @NotNull
    private int productId;

    public OrderItemDto() {
    }

    public OrderItemDto(double price, int quantity, int orderId, int productId) {
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
