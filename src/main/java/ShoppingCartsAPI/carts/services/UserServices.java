package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationServices authenticationServices;

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServices.class);


}
