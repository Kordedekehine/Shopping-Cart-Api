package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.model.WishList;
import ShoppingCartsAPI.carts.repositories.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class WishListServices {

    @Autowired
    WishListRepository wishListRepository;

    public WishListServices(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void addWishes(WishList wishList){
        wishListRepository.save(wishList);
    }

    public List<WishList> findAllWishList(Integer userId){
       return wishListRepository.findAllByUserIdOrderByCreatedDate(userId);
    }
}
