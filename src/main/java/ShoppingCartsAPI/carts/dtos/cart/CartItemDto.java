package ShoppingCartsAPI.carts.dtos.cart;

import ShoppingCartsAPI.carts.model.Carts;
import ShoppingCartsAPI.carts.model.Product;

import javax.validation.constraints.NotNull;

//every thing in the car item

public class CartItemDto {

    private Integer id;
    @NotNull
    private Integer quantity;
    @NotNull
    private Product product;

    public CartItemDto() {
    }

    public CartItemDto(Carts carts){
        this.setId(getId());
        this.setQuantity(getQuantity());
        this.setProduct(getProduct());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
