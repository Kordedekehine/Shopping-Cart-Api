package ShoppingCartsAPI.carts.repositories;

import ShoppingCartsAPI.carts.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Integer> {

}
