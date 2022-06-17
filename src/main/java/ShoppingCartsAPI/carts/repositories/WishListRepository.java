package ShoppingCartsAPI.carts.repositories;

import ShoppingCartsAPI.carts.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface WishListRepository extends JpaRepository<WishList,Integer> {

    List<WishList> findAllByUserIdOrderByCreatedDateDesc(Integer userId);
}
