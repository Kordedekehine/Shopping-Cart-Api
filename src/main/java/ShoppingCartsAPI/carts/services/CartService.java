package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.dtos.cart.AddToCartDto;
import ShoppingCartsAPI.carts.dtos.cart.CartDto;
import ShoppingCartsAPI.carts.dtos.cart.CartItemDto;
import ShoppingCartsAPI.carts.exceptions.CartItemNotExistException;
import ShoppingCartsAPI.carts.model.Carts;
import ShoppingCartsAPI.carts.model.Product;
import ShoppingCartsAPI.carts.model.User;
import ShoppingCartsAPI.carts.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartService {
    @Autowired
private CartRepository cartRepository;

    public CartService() {
    }

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public void addToCart(AddToCartDto addToCartDto, Product product, User user){
        Carts carts = new Carts(product,addToCartDto.getQuantity(),user);
        cartRepository.save(carts);
    }

     public CartDto listCartItems(User user){
         List<Carts> cartsList = cartRepository.findAllByUserOrderByCreatedDate(user);

         List<CartItemDto> cartItemDtos = new ArrayList<>();
         for (Carts carts:cartsList){
             CartItemDto cartItemDto = getDtoFromCart(carts);
             cartItemDtos.add(cartItemDto);
         }
         double totalCost = 0;
         for (CartItemDto cartItemDto:cartItemDtos){
             totalCost += (cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
         //WE COLLATE EVERYTHING TOGETHER
             }
         return new CartDto(cartItemDtos,totalCost);
         //list all the goods in the cart and the overall price of those goods
         }

    public static CartItemDto getDtoFromCart(Carts cart) {
        return new CartItemDto(cart);
    }

     public void updateCartItems(AddToCartDto addToCartDto,User user,Product product){
        Carts carts = cartRepository.getOne(addToCartDto.getId());
        carts.setQuantity(addToCartDto.getQuantity());
        carts.setCreatedDate(new Date());
        cartRepository.save(carts);
     }

     public void deleteCartItems(int id,int userId) throws CartItemNotExistException{
      if (!cartRepository.existsById(id)){
          throw new CartItemNotExistException("Cart id is invalid : " + id);
      }
      cartRepository.deleteById(id);
     }

       public void deleteCartItems(int userId){
        cartRepository.deleteAll();
       }

      public void deleteUserCartItems(User user){
        cartRepository.deleteByUser(user);
      }
}
