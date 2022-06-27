package ShoppingCartsAPI.carts.controller;

import ShoppingCartsAPI.carts.api.ApiResponse;
import ShoppingCartsAPI.carts.dtos.cart.AddToCartDto;
import ShoppingCartsAPI.carts.dtos.cart.CartDto;
import ShoppingCartsAPI.carts.exceptions.AuthenticationFailException;
import ShoppingCartsAPI.carts.exceptions.CartItemNotExistException;
import ShoppingCartsAPI.carts.exceptions.ProductNotExistException;
import ShoppingCartsAPI.carts.model.Product;
import ShoppingCartsAPI.carts.model.User;
import ShoppingCartsAPI.carts.services.AuthenticationServices;
import ShoppingCartsAPI.carts.services.CartService;
import ShoppingCartsAPI.carts.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationServices authenticationService;

   @PostMapping("/addCart/{token}")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam ("token")
   String token)throws AuthenticationFailException, ProductNotExistException {

    authenticationService.authenticate(token); //first authenticate the token
       User user = authenticationService.getUser(token); //then get the user who owns the token
       Product product = productService.getProductById(addToCartDto.getProductId()); //get product id and add product
       System.out.println(product + "added to cart"); //print out added to cart
       cartService.addToCart(addToCartDto,product,user); //add to cart
       return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
   }

   @GetMapping("/listItems")
    public ResponseEntity<CartDto> getCartItems(@RequestParam ("token") String token)
           throws AuthenticationFailException{
       authenticationService.authenticate(token); //first authenticate the token
       User user = authenticationService.getUser(token); //then get the user who owns the token
       CartDto cartDto = cartService.listCartItems(user); //list the items in the user cart
       return new ResponseEntity<>(cartDto,HttpStatus.OK);
   }

    @PutMapping("/update/{cartsItemId}")
   public ResponseEntity<ApiResponse> updateCartItems(@RequestBody @Valid AddToCartDto addToCartDto, @RequestParam
           ("token") String token) throws AuthenticationFailException,ProductNotExistException{
       authenticationService.authenticate(token); //first authenticate the token
        User user = authenticationService.getUser(token); //then get the user who owns the token
        Product product = productService.getProductById(addToCartDto.getProductId());//find the number of the item
        cartService.updateCartItems(addToCartDto,user,product); //then update the item
      return new ResponseEntity<>(new ApiResponse(true,"items successfully updated"),HttpStatus.OK);
   }

   public ResponseEntity<ApiResponse> deleteCartItems(@PathVariable ("cartsItemId") int itemId, @RequestParam
           ("token") String token) throws AuthenticationFailException, CartItemNotExistException{
       authenticationService.authenticate(token); //first authenticate the token
       int userId = authenticationService.getUser(token).getId(); //then get the user id, who owns the token
        cartService.deleteCartItems(userId,itemId); //delete the particular items with the id number
       return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
   }
}
