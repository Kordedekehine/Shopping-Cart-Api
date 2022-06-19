package ShoppingCartsAPI.carts.repositories;

import ShoppingCartsAPI.carts.model.Carts;
import ShoppingCartsAPI.carts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Carts,Integer> {

    List<Carts> findAllByUserOrderByCreatedDate(User user);

    List<Carts> deleteByUser(User user);
}
