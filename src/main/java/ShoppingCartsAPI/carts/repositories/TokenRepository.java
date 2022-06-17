package ShoppingCartsAPI.carts.repositories;

import ShoppingCartsAPI.carts.model.AuthenticationToken;
import ShoppingCartsAPI.carts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken,Integer> {

    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}
