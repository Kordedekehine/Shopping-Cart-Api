package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.repositories.OrderItemsRepository;
import ShoppingCartsAPI.carts.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Value("${BASE_URL}")
    private String baseUrl;

    @Value("${PAYSTACK_SECRET_KEY}")
    private String apiKey;

  //TRYING TO REGISTER TO PAYSTACK AND GENERATE THIER API KEY
}
