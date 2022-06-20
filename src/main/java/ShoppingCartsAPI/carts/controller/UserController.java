package ShoppingCartsAPI.carts.controller;

import ShoppingCartsAPI.carts.dtos.ResponseDto;
import ShoppingCartsAPI.carts.dtos.user.LoginDto;
import ShoppingCartsAPI.carts.dtos.user.LoginResponseDto;
import ShoppingCartsAPI.carts.dtos.user.SignupDto;
import ShoppingCartsAPI.carts.dtos.user.UserUpdateDto;
import ShoppingCartsAPI.carts.exceptions.AuthenticationFailException;
import ShoppingCartsAPI.carts.exceptions.CustomException;
import ShoppingCartsAPI.carts.model.User;
import ShoppingCartsAPI.carts.repositories.UserRepository;
import ShoppingCartsAPI.carts.services.AuthenticationServices;
import ShoppingCartsAPI.carts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationServices authenticationService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
   public List<User> findAllUser(@RequestParam("token")String token) throws AuthenticationFailException{
       authenticationService.authenticate(token);
       return userRepository.findAll();
   }

   @PostMapping("/signup")
   public ResponseDto signup(@RequestBody SignupDto signupDto)throws CustomException{
       return userService.signUp(signupDto);
   }

    @PostMapping("/logIn")
    public LoginResponseDto Login(@RequestBody LoginDto loginDto) throws CustomException {
        return userService.LogIn(loginDto);
    }

        @PostMapping("/updateUser")
    public ResponseDto updateUser(@RequestParam("token") String token, @RequestBody UserUpdateDto userUpdateDto) {
        authenticationService.authenticate(token);
        return userService.updateUser(token, userUpdateDto);
    }
}
