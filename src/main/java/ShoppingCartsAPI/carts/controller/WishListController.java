package ShoppingCartsAPI.carts.controller;

import ShoppingCartsAPI.carts.api.ApiResponse;
import ShoppingCartsAPI.carts.dtos.product.ProductDto;
import ShoppingCartsAPI.carts.model.Product;
import ShoppingCartsAPI.carts.model.User;
import ShoppingCartsAPI.carts.model.WishList;
import ShoppingCartsAPI.carts.services.AuthenticationServices;
import ShoppingCartsAPI.carts.services.ProductService;
import ShoppingCartsAPI.carts.services.WishListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListServices wishListServices;

    @Autowired
    AuthenticationServices authenticationServices;

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token){
        int userID = authenticationServices.getUser(token).getId();
        List<WishList> body = wishListServices.findAllWishList(userID);
        List<ProductDto> products = new ArrayList<>();
        for (WishList wishList : body) {
            products.add(ProductService.getDtoFromProduct(wishList.getProduct()));
        }
         return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/addwishes")
    public ResponseEntity<ApiResponse> addWishes(@RequestBody Product product,@RequestParam("token") String token){
      authenticationServices.authenticate(token);
        User user = authenticationServices.getUser(token);
        WishList wishList = new WishList(user,product);
        wishListServices.addWishes(wishList);
       return new ResponseEntity<>(new ApiResponse(true,"wishes successfully registered"),HttpStatus.OK);
    }
}
