package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;


}
