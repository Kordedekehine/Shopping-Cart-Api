package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartServices {
    @Autowired
private CartRepository cartRepository;

    public CartServices() {
    }

    public CartServices(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


}
