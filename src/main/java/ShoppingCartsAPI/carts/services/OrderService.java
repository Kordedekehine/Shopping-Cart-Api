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
    //sk_live_73d7f631c2c7036cd3975b171c3f4bf47854f96e
    //pk_live_df3093fc1042351ca6d3eb74c695638c9bbbec5c

    @Value("${BASE_URL}")
    private String baseUrl;

    @Value("${PAYSTACK_SECRET_KEY}")
    private String apiKey;

  //TRYING TO REGISTER TO PAYSTACK AND GENERATE THIER API KEY
}
