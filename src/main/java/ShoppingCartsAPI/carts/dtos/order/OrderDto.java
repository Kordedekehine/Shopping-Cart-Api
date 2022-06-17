package ShoppingCartsAPI.carts.dtos.order;

import ShoppingCartsAPI.carts.model.Order;

import javax.validation.constraints.NotNull;

public class OrderDto {

    private Integer id;

    @NotNull
    private Integer userId;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(getId());
        //User cannot set themselves
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
