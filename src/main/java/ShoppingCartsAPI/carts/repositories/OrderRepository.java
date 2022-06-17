package ShoppingCartsAPI.carts.repositories;

import ShoppingCartsAPI.carts.model.Order;
import ShoppingCartsAPI.carts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
