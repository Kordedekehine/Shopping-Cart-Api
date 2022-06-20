package ShoppingCartsAPI.carts.services;

import ShoppingCartsAPI.carts.config.StringMessages;
import ShoppingCartsAPI.carts.dtos.ResponseDto;
import ShoppingCartsAPI.carts.dtos.ResponseStatus;
import ShoppingCartsAPI.carts.dtos.user.*;
import ShoppingCartsAPI.carts.exceptions.AuthenticationFailException;
import ShoppingCartsAPI.carts.exceptions.CustomException;
import ShoppingCartsAPI.carts.model.AuthenticationToken;
import ShoppingCartsAPI.carts.model.Role;
import ShoppingCartsAPI.carts.model.User;
import ShoppingCartsAPI.carts.repositories.UserRepository;
import ShoppingCartsAPI.carts.utils.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static ShoppingCartsAPI.carts.config.StringMessages.USER_CREATED;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationServices authenticationServices;

    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

      public ResponseDto signUp(SignupDto signupDto) throws CustomException{
          // Check to see if the current email address has already been registered.
          if (Checker.notNull(userRepository.findByEmail(signupDto.getEmail()))){
              //if registered already then throw an exception
              throw new CustomException("User already exists");
          }
          //first encrypt the password
          String encryptedPassword = signupDto.getPassword();
          try{
              encryptedPassword = hashPassword(signupDto.getPassword());
          } catch (NoSuchAlgorithmException exception) {
              exception.printStackTrace();
              LOGGER.error("hashing password failed {}",exception.getMessage());
          }
          User user = new User(signupDto.getFirstname(),signupDto.getLastname(),signupDto.getEmail(),
                signupDto.getPhoneNumber(),Role.ROLE_USER,encryptedPassword);

          User newUser;
          try {
             newUser = userRepository.save(user);

             //here we generate and save the new user token
             final AuthenticationToken authenticationToken = new AuthenticationToken(newUser);
             authenticationServices.saveConfirmationToken(authenticationToken);

             //return new ResponseDto(HttpStatus.ACCEPTED.toString(),USER_CREATED);
              return new ResponseDto(ResponseStatus.success.toString(),USER_CREATED);
          } catch (Exception ex){
              throw new CustomException(ex.getMessage());
          }
      }

    public LoginResponseDto LogIn(LoginDto loginDto) throws CustomException{
        // Check to see if the current email address has already been registered.
        User user = userRepository.findByEmail(loginDto.getEmail());
            if (Checker.notNull(user)){
                throw new AuthenticationFailException("User cannot be found");
        }
            try {
                if (!user.getPassword().equals(hashPassword(loginDto.getPassword()))){
                    throw new AuthenticationFailException(StringMessages.WRONG_PASSWORD);
                }
            } catch (NoSuchAlgorithmException exception) {
                exception.printStackTrace();
                LOGGER.error("hashing password failed{}",exception.getMessage());
                throw new RuntimeException(exception);
            }
            AuthenticationToken token = authenticationServices.getToken(user);

            if (!Checker.notNull(token)){
                throw new CustomException("Token not present");
            }
            return new LoginResponseDto("success",token.getToken());
    }
    String hashPassword(String password) throws NoSuchAlgorithmException { //we encode our password
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }

    public ResponseDto createUser(String token, UserCreateDto userCreateDto) throws CustomException,
            AuthenticationFailException{
      User newUser = authenticationServices.getUser(token);
    if (!selectRole(newUser.getRole())){
        //user not allowed to create user
        throw new AuthenticationFailException(StringMessages.USER_NOT_PERMITTED);
    }
    String encryptedPassword = userCreateDto.getPassword();
    try {
        encryptedPassword = hashPassword(userCreateDto.getPassword());
    }catch (NoSuchAlgorithmException exception){
        exception.printStackTrace();
        LOGGER.error("hashing password failed {}",exception.getMessage());
    }
    User user = new User(userCreateDto.getFirstname(),userCreateDto.getLastname(), userCreateDto.getEmail(),
           userCreateDto.getPhoneNumber(), userCreateDto.getRole(),encryptedPassword);
    User createUser;
    try {
        createUser = userRepository.save(user);
        final AuthenticationToken authenticationToken = new AuthenticationToken(newUser);
        authenticationServices.saveConfirmationToken(authenticationToken);
        return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
    } catch (Exception e) {
        // handle user creation fail error
        throw new CustomException(e.getMessage());
    }
      }

      public ResponseDto updateUser(String token, UserUpdateDto userUpdateDto) throws CustomException,
              AuthenticationFailException{
          User newUser = authenticationServices.getUser(token);
          if (selectRole(newUser.getRole())){
              //user not allowed to create user
              throw new AuthenticationFailException(StringMessages.AUTH_TOEKN_NOT_PRESENT);
          }
          //get user by email
          User user = userRepository.findUserById(newUser.getId());

          user.setFirstName(userUpdateDto.getFirstname());
          user.setLastName(userUpdateDto.getLastname());
          user.setRole(userUpdateDto.getRole());

          userRepository.save(user);

          final AuthenticationToken authenticationToken = new AuthenticationToken(newUser);
          authenticationServices.saveConfirmationToken(authenticationToken);
          return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
      }



    /**
    *In the select role method,we confirm that it is either the manager or the admin and not users. if it is the
   * admin or manager then return true, else, return false.
     */
    boolean selectRole(Role role) {
        if (role == Role.ROLE_ADMIN || role == Role.ROLE_MANAGER) {
            return true;
        }
        return false;
    }

    boolean canCrudUser(User userUpdating, Integer userIdBeingUpdated) {
        Role role = userUpdating.getRole();
        // admin and manager can crud any user
        if (role == Role.ROLE_ADMIN || role == Role.ROLE_MANAGER) {
            return true;
        }
        // user can update his own record, but not his role
        if (role == Role.ROLE_USER && userUpdating.getId() == userIdBeingUpdated) {
            return true;
        }
        return false;
    }
}
