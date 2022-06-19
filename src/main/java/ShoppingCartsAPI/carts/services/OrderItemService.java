package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.model.OrderItem;
import ShoppingCartsAPI.carts.repositories.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderItemService {

    @Autowired
    OrderItemsRepository orderItemsRepository;

    public OrderItemService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    public void addOrderedItems(OrderItem orderItem){
        orderItemsRepository.save(orderItem);
    }
}
