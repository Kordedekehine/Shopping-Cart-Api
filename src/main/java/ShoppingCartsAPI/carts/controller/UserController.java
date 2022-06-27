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

//    @GetMapping("/token")
//   public List<User> findAllUser(@RequestParam("token")String token) throws AuthenticationFailException{
//       authenticationService.authenticate(token);
//       return userRepository.findAll();
//   }

    @GetMapping("/listAll")
    public List<User> findAllUser(){
        return userService.getAllUser();
    }


    /**
     * The endpoint for the client to sign up to the app at first
     * @param signupDto it requests for the details of the client to log in
     * @return  it return directly from the business logic in the service class.Check userService to see the logic
     * @throws CustomException if wrong
     */
   @PostMapping("/signup")
   public ResponseDto signup(@RequestBody SignupDto signupDto)throws CustomException{
       return userService.signUp(signupDto);
   }

    /**
     * The endpoint for the log in of the client.
     * @param loginDto it requests for the emails and password to verify if user exists and generate new token
     * @return it return directly from the business logic in the service class
     * @throws CustomException if wrong
     */
    @PostMapping("/login")
    public LoginResponseDto Login(@RequestBody LoginDto loginDto) throws CustomException {
        return userService.LogIn(loginDto);
    }

        @PutMapping("/update/{userId}")
    public ResponseDto updateUser( @RequestBody  UserUpdateDto userUpdateDto,@PathVariable(value = "userId")Integer id) {
        return userService.updateUser( userUpdateDto,id);
    }
}
